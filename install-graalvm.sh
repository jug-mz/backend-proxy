#!/bin/bash
if [ ! -f "/opt/graalvm-ce-19.0.2/bin/java" ]; then
	wget https://github.com/oracle/graal/releases/download/vm-19.0.2/graalvm-ce-linux-amd64-19.0.2.tar.gz -O /opt/graalvm-ce-linux-amd64-19.0.2.tar.gz
	tar -xzf /opt/graalvm-ce-linux-amd64-19.0.2.tar.gz -C /opt
	rm /opt/graalvm-ce-linux-amd64-19.0.2.tar.gz
fi
