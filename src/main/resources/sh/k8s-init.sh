
# 1.关闭selinux等

# 关闭selinux
setenforce 0
sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config
# 查看selinux状态
getenforce

# 关闭防火墙
iptables -F
iptables -X
systemctl stop firewalld
systemctl disable firewalld
systemctl status firewalld

# 关闭 swap
# https://www.jianshu.com/p/6dae5c2c4dab
# (x.x.1)删除 swap 区所有内容
swapoff -a

# (x.x.2)删除 swap 挂载，系统下次启动不会挂载 swap
# 注释文件/etc/fstab中的swap行
nano /etc/fstab
#/dev/mapper/cl-swap     swap                    swap    defaults        0 0



# 2.安装docker

# (x.1)安装docker(略）

# (x.2)解决将要出现的警告[WARNING IsDockerSystemdCheck]
# 参考 https://www.ywcsb.vip/blog/94.html

# 编辑文件/usr/lib/systemd/system/docker.service 
# 添加参数 --exec-opt native.cgroupdriver=systemd
# nano /usr/lib/systemd/system/docker.service
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock --exec-opt native.cgroupdriver=systemd

# 重启
systemctl daemon-reload
systemctl restart docker
docker info | grep Cgroup
#   Cgroup Driver: systemd



# 3.拷贝文件

#/etc/sysctl.d/k8s.conf
#/etc/yum.repos.d/kubernetes.repo

\cp k8s.conf /etc/sysctl.d/k8s.conf
\cp kubernetes.repo /etc/yum.repos.d/kubernetes.repo


# 4.安装kubernetes（指定版本1.19.2）

yum install -y kubelet-1.19.2 kubeadm-1.19.2 kubectl-1.19.2 --disableexcludes=kubernetes
systemctl enable --now kubelet
service kubelet start
modprobe br_netfilter
lsmod | grep br_netfilter

# 查看kubernetes版本
kubeadm version


# 5.拉取k8s镜像

docker pull saved/k8s-kube-apiserver:v1.19.2
docker pull saved/k8s-kube-controller-manager:v1.19.2
docker pull saved/k8s-kube-scheduler:v1.19.2
docker pull saved/k8s-kube-proxy:v1.19.2
docker pull saved/k8s-pause:3.2
docker pull saved/k8s-etcd:3.4.13-0
docker pull saved/k8s-coredns:1.7.0

docker tag saved/k8s-kube-apiserver:v1.19.2 k8s.gcr.io/kube-apiserver:v1.19.2
docker tag saved/k8s-kube-controller-manager:v1.19.2 k8s.gcr.io/kube-controller-manager:v1.19.2
docker tag saved/k8s-kube-scheduler:v1.19.2 k8s.gcr.io/kube-scheduler:v1.19.2
docker tag saved/k8s-kube-proxy:v1.19.2 k8s.gcr.io/kube-proxy:v1.19.2
docker tag saved/k8s-pause:3.2 k8s.gcr.io/pause:3.2
docker tag saved/k8s-etcd:3.4.13-0 k8s.gcr.io/etcd:3.4.13-0
docker tag saved/k8s-coredns:1.7.0 k8s.gcr.io/coredns:1.7.0

docker rmi saved/k8s-kube-apiserver:v1.19.2
docker rmi saved/k8s-kube-controller-manager:v1.19.2
docker rmi saved/k8s-kube-scheduler:v1.19.2
docker rmi saved/k8s-kube-proxy:v1.19.2
docker rmi saved/k8s-pause:3.2
docker rmi saved/k8s-etcd:3.4.13-0
docker rmi saved/k8s-coredns:1.7.0

