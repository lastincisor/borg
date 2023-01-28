#1.修改hostname
hostnamectl set-hostname BE.Worker.1
hostname

#2.修改/etc/hosts文件
#参考文件夹下hosts
# \cp hosts /etc/hosts



#3.重置
kubeadm reset

rm -rf /etc/cni/net.d

#删除k8s网卡（会自动重建），以防cni0的IP地址段与flannel subnet地址段不同
ifconfig flannel.1 down
ip link delete flannel.1

ifconfig cni0 down
ip link delete cni0

iptables -F && iptables -t nat -F
iptables -X


#4.加入集群
kubeadm join 192.168.10.10:6443 --token vdktiw.9wrc21w92jclr6mt \
    --discovery-token-ca-cert-hash sha256:bd9da7912f15721a4a63d1c5ac45845840506886bf5465efb8e0b3c489218d60 


