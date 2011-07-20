#!/bin/sh

lh config \
  --distribution stable --memtest none --binary-indices false \
  --syslinux-timeout 1 --archive-areas "main contrib non-free" \
  --iso-application "Hadoop Live" \
  --iso-publisher "Contrail Project; http://contrail-project.eu" \
  --cache-indices true \
  --iso-volume "Hadoop4Contrail"


# package list
cp cust/hadoop-livecd.list config/chroot_local-packageslists/

# cloudera sources and keys for apt
cp cust/cloudera.list config/chroot_sources/cloudera.chroot
cp cust/cloudera.list config/chroot_sources/cloudera.binary
cp cust/cloudera.gpg  config/chroot_sources/cloudera.chroot.gpg
cp cust/cloudera.gpg  config/chroot_sources/cloudera.binary.gpg

test -f config/chroot_local-packages/xtreemfs-client_1.3.0b1_amd64.deb ||
  wget --directory-prefix=config/chroot_local-packages \
  http://download.opensuse.org/repositories/home:/xtreemfs:/unstable/Debian_6.0/amd64/xtreemfs-client_1.3.0b1_amd64.deb

# we accept the terms of licence
LICENCE_FILE="config/chroot_local-preseed/sun-licence"
echo "sun-java6-bin shared/accepted-sun-dlj-v1-1 select true" >  $LICENCE_FILE
echo "sun-java6-jre shared/accepted-sun-dlj-v1-1 select true" >> $LICENCE_FILE

mkdir -p config/chroot_local-includes/etc/hadoop-0.20/conf.cluster
cp cust/conf.cluster/* config/chroot_local-includes/etc/hadoop-0.20/conf.cluster

install -D -m 755 cust/vmcontext config/chroot_local-includes/etc/init.d/vmcontext
install -D -m 755 cust/start-hadoop.sh config/chroot_local-includes/etc/init.d/hadoop 
install -D -m 755 cust/dnsupdate-client.pl config/chroot_local-includes/usr/bin/dnsupdate-client.pl
install -D -m 755 cust/dnsupdate-server.pl config/chroot_local-includes/usr/bin/dnsupdate-server.pl

mkdir -p config/chroot_local-includes/var/lib
rm -rf config/chroot_local-includes/var/lib/sc-manager
svn up ../webinterface-v2
cp -R ../webinterface-v2 config/chroot_local-includes/var/lib/sc-manager
 
install -D -m 755 cust/one.rb config/chroot_local-includes/var/lib/sc-manager/one.rb

cp cust/post-install.sh config/chroot_local-hooks/00-post-install.sh
#test -f /usr/share/live-helper/hooks/stripped && \
#  cp /usr/share/live-helper/hooks/stripped config/chroot_local-hooks/01-stripped

