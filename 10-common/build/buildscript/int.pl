# 请以关键字加空格打头标识编译环境变量，关键字不区分大小写
# 变量赋值请用等号"="，等号两边支持空格
# PATH,LIB,INCLUDE请以";"结尾，其他变量不需要

# 设置本项目默认环境变量列表(多个关键字请以","隔开)
DEFENV WINDOWS = VC6,ETI,Tornado,InstallShield12
DEFENV LINUX = PPC,PPC_82,ARM,EQT,DAVINCI,SVN

SVN PATH = /usr/local/svn/bin:/usr/local/bin/:/usr/local/erlang/bin:/opt/CodeSourcery/Sourcery_G++_Lite/bin/:/usr/java/jdk1.7.0_67/:/usr/java/jdk1.7.0_67/bin/:/usr/java/jdk1.7.0_67/lib/dt.jar:/usr/java/jdk1.7.0_67/lib/tools.jar:/opt/apache-maven-3.2.3/bin/:/opt/apache-ant-1.9.4/bin/:/usr/java/jdk1.7.0_67/include/:/usr/java/jdk1.7.0_67/jre/lib/i386/client/

# 设置CLEARCASE环境变量
#CLEARCASE CLEARCASE_GROUPS = Domain Users;m_cbb;m_video;m_audio;m_opt;m_mediactrl;m_imageunit;m_vidcomlib;m_vidmanage;m_vidtest;m_vidunit;m_media_codecs;m_h263enc_v300;m_h264enc_v500;m_h264enc_v501;m_h264enc_v600;m_h264dec_v600;m_h264dec_v700;m_mpeg4dec_v500;m_h263enc_opt_v300;m_h264enc_opt_v600;m_h264dec_opt_v700;m_mpeg4dec_opt_v500;m_codec_wrapper;m_imagelib;m_jpegdec

# 设置Linux CLEARCASE环境变量
LinuxCC PATH = /opt/rational/clearcase/bin:/opt/rational/clearcase/etc

# 设置PPC环境变量
PPC PATH = /opt/ppc/bin

# 设置PPC_82环境变量
PPC_82 PATH = /opt/ppc_nofpu/bin

# 设置ARM环境变量
ARM PATH=/opt/arm/bin

# 设置EQT环境变量
EQT PATH=/usr/local/Equator/v6.1/tools/i686_Linux/bin

# 设置DAVINCI环境变量
DAVINCI PATH=/opt/montavista/pro/devkit/arm/v5t_le/bin

# 设置VC6环境变量
VC6 PATH = C:\PROGRA~1\MICROS~4\Common\msdev98\BIN;C:\PROGRA~1\MICROS~4\VC98\BIN;C:\PROGRA~1\MICROS~4\Common\TOOLS\WINNT;C:\PROGRA~1\MICROS~4\Common\TOOLS;
VC6 INCLUDE = C:\PROGRA~1\MICROS~4\VC98\ATL\INCLUDE;C:\PROGRA~1\MICROS~4\VC98\INCLUDE;C:\PROGRA~1\MICROS~4\VC98\MFC\INCLUDE;
VC6 LIB = C:\PROGRA~1\MICROS~4\VC98\LIB;C:\PROGRA~1\MICROS~4\VC98\MFC\LIB;
#VC6 VSCommonDir=C:\PROGRA~1\MICROS~4\Common
VC6 MSDevDir=C:\PROGRA~1\MICROS~4\Common\msdev98
VC6 MSVCDir=C:\PROGRA~1\MICROS~4\VC98


# 设置VC2005环境变量
VC2005 VSINSTALLDIR = C:\Program Files\Microsoft Visual Studio 8
VC2005 VCINSTALLDIR = C:\Program Files\Microsoft Visual Studio 8\VC
VC2005 FrameworkDir = C:\WINNT\Microsoft.NET\Framework
VC2005 FrameworkVersion = v2.0.50727
VC2005 FrameworkSDKDir = C:\Program Files\Microsoft Visual Studio 8\SDK\v2.0
VC2005 DevEnvDir = C:\Program Files\Microsoft Visual Studio 8\Common7\IDE
VC2005 PATH = C:\Program Files\Microsoft Visual Studio 8\Common7\IDE;C:\Program Files\Microsoft Visual Studio 8\VC\BIN;C:\Program Files\Microsoft Visual Studio 8\Common7\Tools;C:\Program Files\Microsoft Visual Studio 8\Common7\Tools\bin;C:\Program Files\Microsoft Visual Studio 8\VC\PlatformSDK\bin;C:\Program Files\Microsoft Visual Studio 8\SDK\v2.0\bin;C:\WINNT\Microsoft.NET\Framework\v2.0.50727;C:\Program Files\Microsoft Visual Studio 8\VC\VCPackages;
VC2005 INCLUDE = C:\Program Files\Microsoft Visual Studio 8\VC\ATLMFC\INCLUDE;C:\Program Files\Microsoft Visual Studio 8\VC\INCLUDE;C:\Program Files\Microsoft Visual Studio 8\VC\PlatformSDK\include;C:\Program Files\Microsoft Visual Studio 8\SDK\v2.0\include;
VC2005 LIB = C:\Program Files\Microsoft Visual Studio 8\VC\ATLMFC\LIB;C:\Program Files\Microsoft Visual Studio 8\VC\LIB;C:\Program Files\Microsoft Visual Studio 8\VC\PlatformSDK\lib;C:\Program Files\Microsoft Visual Studio 8\SDK\v2.0\lib;
VC2005 LIBPATH = C:\WINNT\Microsoft.NET\Framework\v2.0.50727;C:\Program Files\Microsoft Visual Studio 8\VC\ATLMFC\LIB

# 设置iMediaTools环境变量
ETI EQUATOR_ROOT = C:\eti_tools
ETI ETI_TOOLKIT = C:\eti_tools
ETI PATH = C:\eti_tools;

# 设置CCS3.3环境变量
CCS3.3 PATH=C:\CCStudio_v3.3\bin;C:\CCStudio_v3.3\cc\bin;C:\CCStudio_v3.3\c6000\cgtools\bin;C:\CCStudio_v3.3\c6000\evm6x\bin;C:\CCStudio_v3.3\c5400\cgtools\bin;C:\CCStudio_v3.3\c5500\cgtools\bin;C:\CCStudio_v3.3\TMS470\cgtools\bin;C:\CCStudio_v3.3\plugins\bios;C:\CCStudio_v3.3\bios_5_32_01\xdctools;
CCS3.3 C54X_C_DIR=C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\include;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\lib;C:\CCStudio_v3.3\c5400\xdais\include;C:\CCStudio_v3.3\c5500\xdais\lib;C:\CCStudio_v3.3\c5400\cgtools\include;C:\CCStudio_v3.3\c5400\cgtools\lib;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\include\c5400;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\lib\c5400;
CCS3.3 C54X_A_DIR=C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\include;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\lib;C:\CCStudio_v3.3\c5400\dsk5416\include;C:\CCStudio_v3.3\c5400\dsk5416\lib;C:\CCStudio_v3.3\c5400\xdais\include;C:\CCStudio_v3.3\c5500\xdais\lib;C:\CCStudio_v3.3\c5400\cgtools\include;C:\CCStudio_v3.3\c5400\cgtools\lib;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\include\c5400;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\lib\c5400;
CCS3.3 BSL5416_DIR=C:\CCStudio_v3.3\c5400\dsk5416\lib
CCS3.3 TI_DIR=C:\CCStudio_v3.3
CCS3.3 C55X_A_DIR=C:\CCStudio_v3.3\c5500\xdais\include;C:\CCStudio_v3.3\c5500\xdais\lib;C:\CCStudio_v3.3\c5500\cgtools\include;C:\CCStudio_v3.3\c5500\cgtools\lib;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\include;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\lib;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\include\c5500;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\lib\c5500;
CCS3.3 C55X_C_DIR=C:\CCStudio_v3.3\c5500\xdais\include;C:\CCStudio_v3.3\c5500\xdais\lib;C:\CCStudio_v3.3\c5500\cgtools\include;C:\CCStudio_v3.3\c5500\cgtools\lib;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\include;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\lib;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\include\c5500;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\libc5500;
CCS3.3 C55X_CONFIG_FILE=C:\CCStudio_v3.3\cc\bin\c55xx.cfg
CCS3.3 TMS470_C_DIR=C:\CCStudio_v3.3\TMS470\cgtools\include;C:\CCStudio_v3.3\TMS470\cgtools\lib;C:\CCStudio_v3.3\TMS470\rtdx\include;C:\CCStudio_v3.3\TMS470\rtdx\lib;
CCS3.3 TMS470_A_DIR=C:\CCStudio_v3.3\TMS470\cgtools\include;C:\CCStudio_v3.3\TMS470\cgtools\lib;C:\CCStudio_v3.3\TMS470\rtdx\include;C:\CCStudio_v3.3\TMS470\rtdx\lib;
CCS3.3 C6X_C_DIR=C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\include;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\lib;C:\CCStudio_v3.3\c6000\xdais\include;C:\CCStudio_v3.3\c6000\xdais\lib;C:\CCStudio_v3.3\c6000\cgtools\include;C:\CCStudio_v3.3\c6000\cgtools\lib;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\lib\c6000;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\include\c6000;C:\CCStudio_v3.3\c6000\dsk6416\include;C:\CCStudio_v3.3\c6000\dsk6416\lib;C:\CCStudio_v3.3\c6000\dsk6713\include;C:\CCStudio_v3.3\c6000\dsk6713\lib;
CCS3.3 C6X_A_DIR=C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\include;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\bios\lib;C:\CCStudio_v3.3\c6000\xdais\include;C:\CCStudio_v3.3\c6000\xdais\lib;C:\CCStudio_v3.3\c6000\cgtools\include;C:\CCStudio_v3.3\c6000\cgtools\lib;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\lib\c6000;C:\CCStudio_v3.3\bios_5_32_01\packages\ti\rtdx\include\c6000;C:\CCStudio_v3.3\c6000\dsk6416\include;C:\CCStudio_v3.3\c6000\dsk6416\lib;C:\CCStudio_v3.3\c6000\dsk6713\include;C:\CCStudio_v3.3\c6000\dsk6713\lib;
CCS3.3 D_SRC=C:\CCStudio_v3.3\c6000\evm6x\lib
CCS3.3 BSL6416_DIR=C:\CCStudio_v3.3\c6000\dsk6416\lib
CCS3.3 BSL6713_DIR=C:\CCStudio_v3.3\c6000\dsk6713\lib
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
# 设置CCS3.2环境变量

# 设置Tona环境变量
Tornado PATH=c:\Tornado\host\x86-win32\bin;
Tornado WIND_BASE=c:\Tornado
Tornado WIND_HOST_TYPE=x86-win32

# 设置InstallShield12环境变量
InstallShield12 InstallShield12Build=c:\Program Files\Macrovision\IS12\System
InstallShield12 InstallShield12Script=c:\Program Files\Macrovision\IS12\Script

