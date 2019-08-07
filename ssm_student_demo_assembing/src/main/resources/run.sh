#!/usr/bin/env bash
# description: Auto-starts boot

#log路径
LOGPATH=/var/logs/ssm_student_demo
#应用名称
APP_NAME=ssm_student_demo
#shell脚本路径,dirname:为当前文件的目录地址；basename为当前文件名字
SHELLPATH=`cd $(dirname $0) ; pwd`
#日志文件命名规则
LOGFILE=$LOGPATH/$APP_NAME.log
PIDFILE=$SHELLPATH/$(basename $0).pid

PROFILE=$1
#开启远程debug模式
DEBUG_ENABLE=-agentlib:jdwp=transport=dt_socket,address=8010,server=y,suspend=n
#内存溢出
JAVA_MEM_OPTS="-Xmx256m -Xms256m -Xmn256m"

if [ ! -d "$LOGPATH" ]; then
   mkdir -p $LOGPATH
fi
touch "$LOGFILE" 2>/dev/null
[ "$?" != "0" ] && echo "Error:LOGFILE $LOGFILE Permission denied!" && exit 1

# See how we were called.
function start() {
    echo  $LOGFILE
    if [ ! -f $LOGFILE ]; then
        touch $LOGFILE
    fi
    #nohup java -Dappliction=$Tag -Djava.ext.dirs=$Lib":${JAVA_HOME}/jre/lib/ext" $MainClass > $LOGFILE 2>&1 &
    #启动应用，并将日志输出到logfile文件
    nohup java ${DEBUG_ENABLE} ${JAVA_MEM_OPTS} -jar ./"$APP_NAME".jar --spring.profiles.active=dev > $LOGFILE 2>&1 &
    echo $! > "$PIDFILE"
    tailf $LOGFILE
}


function stop() {
    pid=$(ps -ef | grep -v 'grep' | egrep $APP_NAME| awk '{printf $2 " "}')
    if [ "$pid" != "" ]; then
        echo -n "boot ( pid $pid) is running"
        echo
        echo -n $"Shutting down boot: "
        pid=$(ps -ef | grep -v 'grep' | egrep $APP_NAME| awk '{printf $2 " "}')
        if [ "$pid" != "" ]; then
            echo "kill boot process"
            kill -9 "$pid"
        fi
        else
             echo "boot is stopped"
        fi

    status
}

function status()
{
    pid=$(ps -ef | grep -v 'grep' | egrep $APP_NAME| awk '{printf $2 " "}')
    #echo "$pid"
    if [ "$pid" != "" ]; then
        echo "boot is running,pid is $pid"
    else
        echo "boot is stopped"
    fi
}



function usage()
{
   echo "Usage: $0 {start|stop|restart|status}"
   RETVAL="2"
}

# See how we were called.
RETVAL="0"
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        start
        ;;
    reload)
        RETVAL="3"
        ;;
    status)
        status
        ;;
    *)
      usage
      ;;
esac

exit $RETVAL