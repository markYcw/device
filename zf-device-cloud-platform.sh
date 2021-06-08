#！/bin/bash
mvn clean package -Dmaven.test.skip=true > ./zf-device-cloud-platform.txt
dir=./10-common/version/compileinfo
if [ ! -d  $dir ];then
  mkdir -p $dir
else
  echo $dir exist
fi


cp ./zf-device-cloud-platform.txt $dir
sleep 1

if [ ! -d ./10-common/version/release/linux/zf-device-cloud-platform ];then
  mkdir -p ./10-common/version/release/linux/zf-device-cloud-platform
else
  echo ./10-common/version/release/linux/zf-device-cloud-platform exist
fi

cp ./zf-device-core/target/*.jar    ./10-common/version/release/linux/zf-device-cloud-platform/

echo compile over

