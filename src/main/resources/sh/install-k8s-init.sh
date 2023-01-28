#!/bin/bash
cat >> /etc/hosts << EOF
192.168.1.35 master
192.168.1.36 node1
192.168.1.37 node2
EOF

echo "----------操作系统环境初始化----------------"
swapoff -a
#若需要永久关闭swap，需要在/etc/fstab中注释掉swap分区一行
systemctl stop firewalld
systemctl disable firewalld
sed -i 's/enforcing/disabled/' /etc/selinux/config
setenforce 0
echo "net.bridge.bridge-nf-call-iptables = 1 ">>/etc/sysctl.d/k8s.conf
echo "net.bridge.bridge-nf-call-ip6tables = 1 ">>/etc/sysctl.d/k8s.conf
sysctl --system
echo "----------docker-ce以及k8s本地rpm包解压--------------"
tar -zxvf docker20.10_k8s1.20_localrpm.tar.gz -C /opt
echo "----------docker-ce以及kubelet,kubectl等基础组件安装----------------"
cd /opt/docker20.10_k8s1.20_localrpm
yum -y localinstall  *.rpm
systemctl enable docker
systemctl start docker
docker version
systemctl enable kubelet 
systemctl start kubelet

echo "----------docker-ce以及k8s aliyun镜像源配置----------------"
wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo -O /etc/yum.repos.d/docker-ce.repo
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
yum clean all
yum makecache fast