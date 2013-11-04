#!/bin/bash

if (( $# != 3 ))
then
    echo "Usage: glassfish_start_service <path_to_war> <target_port> <threadpool_size>"
    exit
fi

TARGET_PORT=$2
SOURCE_PATH=$1
THREADPOOL_SIZE=$3

if [ -z "$KB_RUNTIME" ]
then
   export KB_RUNTIME=/kb/runtime
fi

if [ -z "$GLASSFISH_HOME" ]
then
   export GLASSFISH_HOME=$KB_RUNTIME/glassfish3
fi

asadmin=$GLASSFISH_HOME/glassfish/bin/asadmin

ps ax | grep "\-Dcom.sun.aas.installRoot=\/kb/runtime/glassfish3/glassfish " > /dev/null
if [ $? -eq 0 ]; then
    echo "Glassfish is already running."
else
    $asadmin start-domain
fi

$asadmin list-virtual-servers | grep server-${TARGET_PORT} > /dev/null
if [ $? -eq 0 ]; then
    echo "Virtual server was already created."
else
    $asadmin create-virtual-server --hosts \$\{com.sun.aas.hostName\} server-${TARGET_PORT}
fi

$asadmin list-threadpools server | grep thread-pool-${TARGET_PORT} > /dev/null
if [ $? -eq 0 ]; then
    echo "Thread pool was already created."
else
    $asadmin create-threadpool --maxthreadpoolsize=${THREADPOOL_SIZE} --minthreadpoolsize=${THREADPOOL_SIZE} thread-pool-${TARGET_PORT}
fi

$asadmin list-http-listeners | grep http-listener-${TARGET_PORT} > /dev/null
if [ $? -eq 0 ]; then
    echo "Http-listener was already created."
else
    $asadmin create-http-listener --listeneraddress 0.0.0.0 --listenerport ${TARGET_PORT} --default-virtual-server server-${TARGET_PORT} --securityEnabled=false --acceptorthreads=${THREADPOOL_SIZE} http-listener-${TARGET_PORT}
    $asadmin set server.network-config.network-listeners.network-listener.http-listener-${TARGET_PORT}.thread-pool=thread-pool-${TARGET_PORT}
    $asadmin set server.network-config.protocols.protocol.http-listener-${TARGET_PORT}.http.timeout-seconds=1800
fi

$asadmin list-applications | grep app-${TARGET_PORT} > /dev/null
if [ $? -eq 0 ]; then
    $asadmin undeploy app-${TARGET_PORT}
fi

if [ "$KB_DEPLOYMENT_CONFIG" ]; then
    echo "Setting global glassfish property KB_DEPLOYMENT_CONFIG=$KB_DEPLOYMENT_CONFIG"
    $asadmin create-system-properties KB_DEPLOYMENT_CONFIG=$KB_DEPLOYMENT_CONFIG
fi

if [ "$KB_SERVICE_NAME" ]; then
    echo "Setting global glassfish property KB_SERVICE_NAME=$KB_SERVICE_NAME"
    $asadmin create-system-properties KB_SERVICE_NAME=$KB_SERVICE_NAME
fi

$asadmin deploy --virtualservers server-${TARGET_PORT} --contextroot / --name app-${TARGET_PORT} ${SOURCE_PATH}

curl -s localhost:${TARGET_PORT} | grep 32603 > /dev/null
if [ $? -eq 0 ]; then
	echo "The server failed to start up successfully and is running in protected mode. Please check the system and glassfish logs."
fi
