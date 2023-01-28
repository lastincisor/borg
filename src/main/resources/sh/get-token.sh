# 获取token,复制token备用
kubeadm token list
# 若输出的token创建时间超过24小时可运行下面的命令重新创建，否则请无视
kubeadm token create
# 获取token sha256，复制备用
openssl x509 -pubkey -in /etc/kubernetes/pki/ca.crt | openssl rsa -pubin -outform der 2>/dev/null | openssl dgst -sha256 -hex | sed 's/^.* //'

