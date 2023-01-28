#1.修改hostname
hostnamectl set-hostname BE.Master
hostname

#2.修改/etc/hosts文件
#参考文件夹下hosts
# \cp hosts /etc/hosts


#3.重置
kubeadm reset 

rm -rf /etc/kubernetes/pki
rm -rf /etc/cni/net.d
rm -rf $HOME/.kube

#删除k8s网卡（会自动重建），以防cni0的IP地址段与flannel subnet地址段不同
ifconfig flannel.1 down    
ip link delete flannel.1

ifconfig cni0 down    
ip link delete cni0

iptables -F && iptables -t nat -F
iptables -X
 


#4.初始化 kubeadm
kubeadm init --kubernetes-version=v1.19.2 --apiserver-advertise-address 192.168.10.10 --pod-network-cidr=10.244.0.0/16 

#5.创建kubectl
rm -rf $HOME/.kube
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config 

#6.去掉scheduler、controller-manager端口限制
#https://blog.csdn.net/xiaobao7865/article/details/107513957
#编辑配置文件：/etc/kubernetes/manifests/kube-scheduler.yaml  、/etc/kubernetes/manifests/kube-controller-manager.yaml
nano /etc/kubernetes/manifests/kube-scheduler.yaml
nano /etc/kubernetes/manifests/kube-controller-manager.yaml
#删除--port=0（注意是删除，在行首加#会出错的） 
#重启
systemctl restart kubelet

#7.修改NodePort对外映射端口范围（默认为30000-50000）
#https://blog.cnrainbird.com/index.php/2020/05/05/k8s_mac_xia_nodeport_fan_wei_xiu_gai_yun_xu_fan_wei_30000-32767/
#https://www.cnblogs.com/minseo/p/12606326.html
nano /etc/kubernetes/manifests/kube-apiserver.yaml
#添加 - --service-node-port-range=1-65535
#重启
systemctl restart kubelet

#8.拉取flannel镜像 
docker pull saved/k8s-coreos-flannel:v0.13.0 
docker tag saved/k8s-coreos-flannel:v0.13.0 quay.io/coreos/flannel:v0.13.0
docker rmi saved/k8s-coreos-flannel:v0.13.0


#9.安装网络插件 flannel

#(x.1)下载yml
# wget https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
#https://blog.csdn.net/fanren224/article/details/86348037 

#(x.2)解决flannel因网卡名启动失败问题
#https://blog.csdn.net/ygqygq2/article/details/81698193
#flannel 默认会使用主机的第一张网卡，如果有多张网卡，需要通过配置单独指定
#修改 kube-flannel-0.13.0.yml添加 - --iface=ens3       (ens3改为实际网卡名，可通过ifconfig获得，可多行)
        - --iface=ens3
        - --iface=eth0
        #- --iface-regex=eth*|ens*

#(x.3)解决Failed to ensure iptables rules: Error checking rule existence: failed to check rule existence: running
#https://blog.csdn.net/u012516914/article/details/103480784
#修改 kube-flannel-0.13.0.yml 调高limits
      containers:
      - name: kube-flannel
        resources:
          requests:
            cpu: "100m"
            memory: "50Mi"
          limits:
            cpu: "500m"
            memory: "500Mi"

#(x.4)安装flannel
kubectl apply -f kube-flannel-0.13.0.yml

#(x.5)查看 flannel 是否正常运行
kubectl get pod -n kube-system
# 查看日志(根据实际情况修改 kube-flannel-ds-amd64-q8kvb )
kubectl describe pod kube-flannel-ds-762vr -n kube-system
 
#10.查看状态
kubectl get node
kubectl get pod -o wide --all-namespaces 
kubectl get ComponentStatus
# 查看kubelet，是否存在异常日志
journalctl -u kubelet -f

kubectl logs kube-flannel-ds-6tsq8   -n kube-system

