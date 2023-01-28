
#1.查询安装过的软件包
yum list installed | grep kubelet
yum list installed | grep kubeadm
yum list installed | grep kubectl

#kubelet.x86_64  1.20.0-0  @kubernetes

#2.删除安装的软件包
yum -y remove kubelet.x86_64
yum -y remove kubeadm.x86_64
yum -y remove kubectl.x86_64

