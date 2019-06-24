#!/bin/bash
if [ ! -d "/opt/graalvm-ce-1.0.0-rc16/bin/native-image" ]; then 
	wget https://github.com/oracle/graal/releases/download/vm-1.0.0-rc16/graalvm-ce-1.0.0-rc16-linux-amd64.tar.gz -O /opt/graalvm-ce-1.0.0-rc16-linux-amd64.tar.gz
	tar -xzf /opt/graalvm-ce-1.0.0-rc16-linux-amd64.tar.gz -C /opt
fi
