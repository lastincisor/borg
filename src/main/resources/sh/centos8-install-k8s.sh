cat >> /etc/hosts <<EOF
192.168.1.73  node1
192.168.1.75  node2
192.168.1.76  node3
EOF



三台都执行，以下无特殊说明，则三台都执行
systemctl stop firewalld
systemctl disable firewalld
sed -i 's/enforcing/disabled/' /etc/selinux/config
setenforce 0
getenforce



swapoff -a   //临时关闭
vi /etc/fstab    //永久关闭（注释掉最后一条配置）
...
#/dev/mapper/centos-swap swap                    swap    defaults        0 0

free -h | grep Swap   //验证Swap关闭情况（显示0代表成功关闭）
Swap:            0B          0B          0B



cat > /etc/sysctl.d/k8s.conf <<EOF
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
net.ipv4.ip_forward = 1
vm.swappiness = 0
EOF
sysctl --system


yum makecache      //更新yum软件包索引

yum -y install yum-utils

#yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce
yum install -y yum-utils device-mapper-persistent-data lvm2


yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo


sudo yum -y install docker-ce --allowerasing

systemctl start docker
systemctl enable docker


docker version
docker info

sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://ccdkz6eh.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker




cat >> /etc/yum.repos.d/kubernetes.repo << EOF
[kubernetes]
name=Kubernetes Repo
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
enabled=1
EOF

yum  list  |grep kubeadm
yum -y install kubelet-1.22.3-0 kubeadm-1.22.3-0 kubectl-1.22.3-0  //指定版本号部署
 //配置开机自启动
systemctl enable kubelet

kubeadm init --kubernetes-version=v1.22.3 \
--apiserver-advertise-address=192.168.1.73   \
--image-repository registry.aliyuncs.com/google_containers  \
--service-cidr=10.10.0.0/16 --pod-network-cidr=10.122.0.0/16


kubeadm reset

kubeadm init --kubernetes-version=v1.22.3 \
--apiserver-advertise-address=192.168.1.73   \
--image-repository registry.aliyuncs.com/google_containers  \
--service-cidr=10.10.0.0/16 --pod-network-cidr=10.122.0.0/16


##kubeadm config images pull卡死，解决方案
1、kubeadm config images list
	k8s.gcr.io/kube-apiserver:v1.22.6
	k8s.gcr.io/kube-controller-manager:v1.22.6
	k8s.gcr.io/kube-scheduler:v1.22.6
	k8s.gcr.io/kube-proxy:v1.22.6
	k8s.gcr.io/pause:3.5
	k8s.gcr.io/etcd:3.5.0-0
	k8s.gcr.io/coredns/coredns:v1.8.4
2、从阿里云仓库中pull
docker pull registry.aliyuncs.com/google_containers/kube-apiserver:v1.22.6
docker pull registry.aliyuncs.com/google_containers/kube-controller-manager:v1.22.6
docker pull registry.aliyuncs.com/google_containers/kube-scheduler:v1.22.6
docker pull registry.aliyuncs.com/google_containers/kube-proxy:v1.22.6
docker pull registry.aliyuncs.com/google_containers/pause:3.5
docker pull registry.aliyuncs.com/google_containers/etcd:3.5.0-0
docker pull registry.aliyuncs.com/google_containers/coredns:v1.8.4
3、转换标签
docker tag registry.aliyuncs.com/google_containers/kube-apiserver:v1.22.6	 k8s.gcr.io/kube-apiserver:v1.22.6
docker tag registry.aliyuncs.com/google_containers/kube-controller-manager:v1.22.6	 k8s.gcr.io/kube-controller-manager:v1.22.6
docker tag registry.aliyuncs.com/google_containers/kube-scheduler:v1.22.6 	k8s.gcr.io/kube-scheduler:v1.22.6
docker tag registry.aliyuncs.com/google_containers/kube-proxy:v1.22.6   	k8s.gcr.io/kube-proxy:v1.22.6
docker tag registry.aliyuncs.com/google_containers/pause:3.5	 k8s.gcr.io/pause:3.5
docker tag registry.aliyuncs.com/google_containers/etcd:3.5.0-0     k8s.gcr.io/etcd:3.5.0-0
docker tag registry.aliyuncs.com/google_containers/coredns:v1.8.4   k8s.gcr.io/coredns/coredns:1.3.1



如果报错：
[kubelet-check] The HTTP call equal to 'curl -sSL http://localhost:10248/healthz' failed with error: Get "http://localhost:10248/healthz": dial tcp [::1]:10248: connect: connection refused.
编辑 /etc/docker/daemon.json 添加如下启动项参数即可：
{
  "exec-opts": ["native.cgroupdriver=systemd"]
}
重置Kubelet组件后重新执行init
[root@master ~]# kubeadm reset



[root@master ~]# kubeadm init --kubernetes-version=v1.22.3 \
> --apiserver-advertise-address=192.168.157.11   \
> --image-repository registry.aliyuncs.com/google_containers  \
> --service-cidr=10.10.0.0/16 --pod-network-cidr=10.122.0.0/16
[init] Using Kubernetes version: v1.22.3
[preflight] Running pre-flight checks
        [WARNING FileExisting-tc]: tc not found in system path
[preflight] Pulling images required for setting up a Kubernetes cluster
[preflight] This might take a minute or two, depending on the speed of your internet connection
[preflight] You can also perform this action in beforehand using 'kubeadm config images pull'
[certs] Using certificateDir folder "/etc/kubernetes/pki"
[certs] Generating "ca" certificate and key
[certs] Generating "apiserver" certificate and key
[certs] apiserver serving cert is signed for DNS names [kubernetes kubernetes.default kubernetes.default.svc kubernetes.default.svc.cluster.local master] and IPs [10.10.0.1 192.168.157.11]
[certs] Generating "apiserver-kubelet-client" certificate and key
[certs] Generating "front-proxy-ca" certificate and key
[certs] Generating "front-proxy-client" certificate and key
[certs] Generating "etcd/ca" certificate and key
[certs] Generating "etcd/server" certificate and key
[certs] etcd/server serving cert is signed for DNS names [localhost master] and IPs [192.168.157.11 127.0.0.1 ::1]
[certs] Generating "etcd/peer" certificate and key
[certs] etcd/peer serving cert is signed for DNS names [localhost master] and IPs [192.168.157.11 127.0.0.1 ::1]
[certs] Generating "etcd/healthcheck-client" certificate and key
[certs] Generating "apiserver-etcd-client" certificate and key
[certs] Generating "sa" key and public key
[kubeconfig] Using kubeconfig folder "/etc/kubernetes"
[kubeconfig] Writing "admin.conf" kubeconfig file
[kubeconfig] Writing "kubelet.conf" kubeconfig file
[kubeconfig] Writing "controller-manager.conf" kubeconfig file
[kubeconfig] Writing "scheduler.conf" kubeconfig file
[kubelet-start] Writing kubelet environment file with flags to file "/var/lib/kubelet/kubeadm-flags.env"
[kubelet-start] Writing kubelet configuration to file "/var/lib/kubelet/config.yaml"
[kubelet-start] Starting the kubelet
[control-plane] Using manifest folder "/etc/kubernetes/manifests"
[control-plane] Creating static Pod manifest for "kube-apiserver"
[control-plane] Creating static Pod manifest for "kube-controller-manager"
[control-plane] Creating static Pod manifest for "kube-scheduler"
[etcd] Creating static Pod manifest for local etcd in "/etc/kubernetes/manifests"
[wait-control-plane] Waiting for the kubelet to boot up the control plane as static Pods from directory "/etc/kubernetes/manifests". This can take up to 4m0s
[apiclient] All control plane components are healthy after 6.011916 seconds
[upload-config] Storing the configuration used in ConfigMap "kubeadm-config" in the "kube-system" Namespace
[kubelet] Creating a ConfigMap "kubelet-config-1.22" in namespace kube-system with the configuration for the kubelets in the cluster
[upload-certs] Skipping phase. Please see --upload-certs
[mark-control-plane] Marking the node master as control-plane by adding the labels: [node-role.kubernetes.io/master(deprecated) node-role.kubernetes.io/control-plane node.kubernetes.io/exclude-from-external-load-balancers]
[mark-control-plane] Marking the node master as control-plane by adding the taints [node-role.kubernetes.io/master:NoSchedule]
[bootstrap-token] Using token: pebsnd.5g1his0be5152t0g
[bootstrap-token] Configuring bootstrap tokens, cluster-info ConfigMap, RBAC Roles
[bootstrap-token] configured RBAC rules to allow Node Bootstrap tokens to get nodes
[bootstrap-token] configured RBAC rules to allow Node Bootstrap tokens to post CSRs in order for nodes to get long term certificate credentials
[bootstrap-token] configured RBAC rules to allow the csrapprover controller automatically approve CSRs from a Node Bootstrap Token
[bootstrap-token] configured RBAC rules to allow certificate rotation for all node client certificates in the cluster
[bootstrap-token] Creating the "cluster-info" ConfigMap in the "kube-public" namespace
[kubelet-finalize] Updating "/etc/kubernetes/kubelet.conf" to point to a rotatable kubelet client certificate and key
[addons] Applied essential addon: CoreDNS
[addons] Applied essential addon: kube-proxy

Your Kubernetes control-plane has initialized successfully!

To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

Alternatively, if you are the root user, you can run:

  export KUBECONFIG=/etc/kubernetes/admin.conf

You should now deploy a pod network to the cluster.
Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

Then you can join any number of worker nodes by running the following on each as root:

kubeadm join 192.168.157.11:6443 --token pebsnd.5g1his0be5152t0g \
        --discovery-token-ca-cert-hash sha256:d70ddea271d38a36d211570e5d049c3f08cdcbeac3b60573ac88768ac874d915 
[root@master ~]# 


出现上面successfully`信息之后，表示初始化已经完成

上面kubeadm join完整命令，因为后续node节点加入集群是需要用到，其中包含token



[root@master ~]# kubectl get node
NAME     STATUS     ROLES                  AGE    VERSION
master   NotReady   control-plane,master   102s   v1.22.3
node1    NotReady   <none>                 16s    v1.22.3
node2    NotReady   <none>                 11s    v1.22.3



apiVersion: policy/v1beta1
kind: PodSecurityPolicy
metadata:
  name: psp.flannel.unprivileged
  annotations:
    seccomp.security.alpha.kubernetes.io/allowedProfileNames: docker/default
    seccomp.security.alpha.kubernetes.io/defaultProfileName: docker/default
    apparmor.security.beta.kubernetes.io/allowedProfileNames: runtime/default
    apparmor.security.beta.kubernetes.io/defaultProfileName: runtime/default
spec:
  privileged: false
  volumes:
  - configMap
  - secret
  - emptyDir
  - hostPath
  allowedHostPaths:
  - pathPrefix: "/etc/cni/net.d"
  - pathPrefix: "/etc/kube-flannel"
  - pathPrefix: "/run/flannel"
  readOnlyRootFilesystem: false
  # Users and groups
  runAsUser:
    rule: RunAsAny
  supplementalGroups:
    rule: RunAsAny
  fsGroup:
    rule: RunAsAny
  # Privilege Escalation
  allowPrivilegeEscalation: false
  defaultAllowPrivilegeEscalation: false
  # Capabilities
  allowedCapabilities: ['NET_ADMIN', 'NET_RAW']
  defaultAddCapabilities: []
  requiredDropCapabilities: []
  # Host namespaces
  hostPID: false
  hostIPC: false
  hostNetwork: true
  hostPorts:
  - min: 0
    max: 65535
  # SELinux
  seLinux:
    # SELinux is unused in CaaSP
    rule: 'RunAsAny'
---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: flannel
rules:
- apiGroups: ['extensions']
  resources: ['podsecuritypolicies']
  verbs: ['use']
  resourceNames: ['psp.flannel.unprivileged']
- apiGroups:
  - ""
  resources:
  - pods
  verbs:
  - get
- apiGroups:
  - ""
  resources:
  - nodes
  verbs:
  - list
  - watch
- apiGroups:
  - ""
  resources:
  - nodes/status
  verbs:
  - patch
---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: flannel
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: flannel
subjects:
- kind: ServiceAccount
  name: flannel
  namespace: kube-system
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: flannel
  namespace: kube-system
---
kind: ConfigMap
apiVersion: v1
metadata:
  name: kube-flannel-cfg
  namespace: kube-system
  labels:
    tier: node
    app: flannel
data:
  cni-conf.json: |
    {
      "name": "cbr0",
      "cniVersion": "0.3.1",
      "plugins": [
        {
          "type": "flannel",
          "delegate": {
            "hairpinMode": true,
            "isDefaultGateway": true
          }
        },
        {
          "type": "portmap",
          "capabilities": {
            "portMappings": true
          }
        }
      ]
    }
  net-conf.json: |
    {
      "Network": "10.122.0.0/16",
      "Backend": {
        "Type": "vxlan"
      }
    }
---
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: kube-flannel-ds
  namespace: kube-system
  labels:
    tier: node
    app: flannel
spec:
  selector:
    matchLabels:
      app: flannel
  template:
    metadata:
      labels:
        tier: node
        app: flannel
    spec:
      affinity:
        nodeAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
            nodeSelectorTerms:
            - matchExpressions:
              - key: kubernetes.io/os
                operator: In
                values:
                - linux
      hostNetwork: true
      priorityClassName: system-node-critical
      tolerations:
      - operator: Exists
        effect: NoSchedule
      serviceAccountName: flannel
      initContainers:
      - name: install-cni
        image: quay.io/coreos/flannel:v0.14.0
        command:
        - cp
        args:
        - -f
        - /etc/kube-flannel/cni-conf.json
        - /etc/cni/net.d/10-flannel.conflist
        volumeMounts:
        - name: cni
          mountPath: /etc/cni/net.d
        - name: flannel-cfg
          mountPath: /etc/kube-flannel/
      containers:
      - name: kube-flannel
        image: quay.io/coreos/flannel:v0.14.0
        command:
        - /opt/bin/flanneld
        args:
        - --ip-masq
        - --kube-subnet-mgr
        resources:
          requests:
            cpu: "100m"
            memory: "50Mi"
          limits:
            cpu: "100m"
            memory: "50Mi"
        securityContext:
          privileged: false
          capabilities:
            add: ["NET_ADMIN", "NET_RAW"]
        env:
        - name: POD_NAME
          valueFrom:
            fieldRef:
              fieldPath: metadata.name
        - name: POD_NAMESPACE
          valueFrom:
            fieldRef:
              fieldPath: metadata.namespace
        volumeMounts:
        - name: run
          mountPath: /run/flannel
        - name: flannel-cfg
          mountPath: /etc/kube-flannel/
      volumes:
      - name: run
        hostPath:
          path: /run/flannel
      - name: cni
        hostPath:
          path: /etc/cni/net.d
      - name: flannel-cfg
        configMap:
          name: kube-flannel-cfg




[root@node1 ~]# kubectl get pods --all-namespaces
The connection to the server localhost:8080 was refused - did you specify the right host or port?

拷贝master节点的/etc/kubernetes/admin.conf配置文件到/etc/kubernetes/目录下，并执行如下命令
 mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config
