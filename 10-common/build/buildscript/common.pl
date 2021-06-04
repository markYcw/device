#!usr/bin/perl
# ======================================================================
# ABC ABC
# Perl Source File -- Created with SAPIEN Technologies PrimalScript 3.1
#
# NAME: common.pl
#
# AUTHOR: pengjing , chenhuiren
# DATE  : 2007-7-26 ~ 2007-9-27
#
# PURPOSE: Global Var Set , Not include module build process , Require "version" input
#
# ======================================================================
# log file define -----
# BuildError.log
# BuildInfo.txt
# checkfile.log
# checkreadme.log
# notify.log
#
# define variable ab. -----
# P = path
# N = name
# F = file
# D = dir
# R = release
# S = result
# L = log
# M = mail
# W = Whole ( Whole Path )
# CF = config file
# IF = info file
# RM = readme config
# RRM = release readme
# CRM = check readme
# PM = Product Manager
# TE = Test Engineer
# prj = project
# ver = version
# modu = module
# err = error
# 
# special global variable define -----
# $OS : Operation System ( 1 = linux / 0 = windows )
# $OSstring : Operation System System ( "linux" / "windows" )
# $builddatetime : Current yymmdd-hhmm
# $builddate : Current yymmdd
# $workpath : Script work path
# $version : Specific Project Version Name
# $verworkP :
# $vererrL :
# $place : $module : 
# $module : 
# BUILDINFO : BuildInfo.txt
# BUILDERROR : BuildError.log
# $UCMprj :
# $versionCFN :
# $commoncompP :
# $compileIFP :
# $moduleCFP :
# $checkreadme :
# $releasekreadme :
# $PMM :
# $CMOM :
# $CMOM :
# $SMTP :
# $SMTP :
# $mailfrom :
# $mailfrom :
# $sendmail :
# $connect_server :
# $connect_user :
# $connect_passwd : 
# $DynviewN :
# $SnapviewP :
# $ReleaseP :
# $TEM :
# =====================================================================================================================
# ����common.pl֮ǰ,Ӧ���Ȼ�ȡ���α������Ŀ�汾��
# ��ȡ��ǰ����ϵͳ
if ( $ENV{'OS'} =~ /windows/i )
{
	$OS = 0; # Windows����ϵͳ
	$OSstring = "Windows";
}
else
{
	$OS = 1; # Unix����ϵͳ
	$OSstring = "Linux";
}
# ��ȡ��ǰʱ��͹���·��
$builddatetime = &getdatetime;
$builddate = &getdate;
$buildtime = &get_nowtime;
# ��ȡ��ǰ����·��
if ( $workpath eq "" )
{
	$workpath = &getcurrpath;
}
else
{
	$workpath = &revisepath(1,$workpath);
}
# =====================================================================================================================
# �ű�����Ŀ¼�µ��԰汾���������ļ��в������򴴽�
if ( !-e $workpath.$version && !mkdir($workpath.$version) )
{
	$place = "Make_Version_Dir";
	print "Failed Make Dir '$workpath$version' !\n";
	&command("pause");
	exit 1; # �����汾���������ļ���ʧ�����˳���������
}
$verworkP = &revisepath(1,$workpath.$version); # ��������·���µİ汾·��Ϊ���ø�ʽ , ����ֱ����log�ļ�����
# �ڰ汾���������ļ����´����Խű�����ʱ�������������ļ���
if ( !-e $verworkP.$builddatetime && !mkdir($verworkP.$builddatetime) )
{
	$place = "Make_Version_Datetime_Dir";
	print "Failed Make Dir '$verworkP$builddatetime' !\n";
	&command("pause");
	exit 1; # ��������ʱ���������ļ���ʧ�����˳���������
}
$verworkP = &revisepath(1,$verworkP.$builddatetime); # ��������·���µİ汾·��Ϊ���ø�ʽ , ����ֱ����log�ļ�����
# �򿪱��������Ϣ��־BuildError.log׼��д��
if ( !open(BUILDERROR,">>$verworkP"."BuildError.log") )
{
	print "Error : Failed Open File '$verworkP"."BuildError.log' !\n";
	&command("pause");
	exit 1; # �򲻿���־�ļ����˳���������
}
&printerror(1,"OS : $OSstring\nAutoBuild Script Path : $workpath\nAutoBuild Time : $builddatetime\n");
$place = ""; # ���ڼ�¼�ӳ�����
$module = ""; # ���ڼ�¼ģ����
$vererrL = $verworkP."error.log"; # ����error.log�����ڼ�¼ϵͳ����Ĵ�����Ϣ�������ֱ�ӵ��ø�·��
if ( $OS )
{
	my $info = `unalias cp`;
	my $str = "Unalias cp Successfully !\n";
	$str = "Unalias cp info : $info\n" if ( $info != /^\s*$/);
	&printerror(1,$str);
}
# =====================================================================================================================
# ����Ŀ�����ļ�main.ini����ȡ����������Ϣ
$place = "Get_Project_Config";
print "\n$module - $place......\n";
if ( !open(PROJECT,$workpath."main.ini") )
{
	&printerror(0,"Failed Open File '$workpath"."main.ini' !\n");
	&command("pause");
	exit 1; # �򲻿���Ŀ�����ļ�main.ini���˳���������
}
local @prjfile = <PROJECT>;
close(PROJECT);
chomp(@prjfile);
# ��ȡmain.ini�о�̬��Ϣ
$BuildServer = &getprjconf("hostname");
$proline = &getprjconf("productline");
$UCMprj = &getprjconf("UCMproject"); # ��ȡ��ǰ������Ŀ
#print "zhangtingting:[$UCMprj]\n";

$compileinfo_p = &getprjconf("compileinfo_path");
$compileinfo_p = &revisepath(1,$compileinfo_p);



&printerror(1,"Build Server : $BuildServer\nUCMproject : $UCMprj\n");
$commoncompP = &getprjconf("commoncomp_path");
$compileIFP = &getprjconf("compileinfo_path");
$moduleCFP = &getprjconf("moduleconf_path");
my $linux_ismount = &getprjconf("linux_ismount");
$ISM = 0; # ���û���д��checkreadme��ϢתΪboolֵ�������ж�
$ISM = 1 if (( $linux_ismount eq "" ) || ( $linux_ismount eq "yes" ) || ( $linux_ismount eq "y" )); # ������дֵΪ�ջ�������"yes"ʱ��Ϊ��
undef($linux_ismount);
undef(@prjfile);
# У����Ŀ�����ļ���
if ( $UCMprj eq "" )
{
	&printerror(0,"Failed Found Key Value 'UCMproject' in '$workpath"."main.ini' !\n");
	&command("pause");
	exit 1; # ��ȡ���Ĺؼ���ϢΪ�����˳���������
}
# У��ؼ�ͨ��������Ϣ
if ( $commoncompP eq "" )
{
	&printerror(0,"Failed Found Key Value 'commoncomp_path' in '$workpath"."main.ini' !\n");
	&command("pause");
	exit 1; # ��ȡ���Ĺؼ���ϢΪ�����˳���������
}
# У��ؼ�ͨ��������Ϣ
if ( $compileIFP eq "" )
{
	&printerror(0,"Failed Found Key Value 'compileinfo_path' in '$workpath"."main.ini' !\n");
	&command("pause");
	exit 1; # ��ȡ���Ĺؼ���ϢΪ�����˳���������
}
# У��ؼ�ͨ��������Ϣ
if ( $moduleCFP eq "" )
{
	&printerror(0,"Failed Found Key Value 'moduleconf_path' in '$workpath"."main.ini' !\n");
	&command("pause");
	exit 1; # ��ȡ���Ĺؼ���ϢΪ�����˳���������
}
# ���û���д��·������Ϊ���ø�ʽ
$commoncompP = &revisepath(0,$commoncompP);
$compileIFP = &revisepath(0,$compileIFP);
$moduleCFP = &revisepath(0,$moduleCFP);
# ����Ŀ�汾�����ļ�����ȡ����������Ϣ
$place = "Get_Version_Config";
print "\n$module - $place......\n";

print "workpath:[$workpath]\n";
print "UCMprj.ini:[$workpath.$UCMprj.ini]\n";
my $temp_path = $workpath."$UCMprj.ini";
print "temp_path:[$temp_path]\n";

#$workpath."$UCMprj.ini";

if ( !open(VERSION,$temp_path) )
{
	
	&printerror(0,"Failed Open file '$workpath$UCMprj".".ini' !\n");
	&command("pause");
	exit 1; # �򲻿���Ŀ�汾�����ļ����˳���������
}else{
  #print "hhiiiiii.......\n";

}
@verfile = <VERSION>;
close(VERSION);
chomp(@verfile);
# ��ȡproject.ini�о�̬��Ϣ
if ( $OS )
{
	@pv = &getline(0,"l",@verfile); # ���ұ���ƽ̨��
	@pv = &shiftvalue("l",@pv); # ȥ������ƽ̨��ͷ��ֵ�Ϳո�
}
else
{
	@pv = &getline(0,"w",@verfile); # ���ұ���ƽ̨��
	@pv = &shiftvalue("w",@pv); # ȥ������ƽ̨��ͷ��ֵ�Ϳո�
}
undef(@verfile);
@v = &getline(0,$version,@pv);
undef(@pv);
if ( @v == 0 )
{
	&printerror(0,"Failed Found Version Config '$version' in '$workpath$versionCFN' !\n","v");
	&command("pause");
	exit 1; # �Ҳ����汾�����ڵ������˳���������
}

######hanjian 20140612##########################
#�����ǲ��ǿ���ȡ���У�(�±�)
#������main.ini�����һ��?�����������м���Ԫ�� ���ü���,�� forѭ��?
#����ʱ�ڹ��������в��Ұ���.svn��.git�ļ������ж���svn������������git��������
#Ϊ�˲�Ӱ��ģ����룬���޸�֮ǰ�Ĳ��֣�ֻ��ģ�������±����һ��ѭ��������Ϊ��������ȡ����λ��
################################################

@vervalue = &getvalue (" ",$v[0]); # ����ҵ����н����ҵ��ĵ�һ����ȡֵ 1����ƽ̨ 2�汾�� 3��̬��ͼ�� 4��̬��ͼ����λ�� 5����λ�� 6���Ը������ʼ���ַ

if ( @vervalue < 4 ) # ����һ��Ӧ������ǰ5��ֵ , ����ȥ���˱���ƽֵ̨ , ���У��Ϊ4��ֵ
{
	&printerror(0,"Wrong Format in '$v[0]' !\n","v");
	&command("pause");
	exit 1; # �ҵ��汾�����ڵ��е����йؼ���ȱ�����˳���������
}
$SnapviewP = &revisepath(1,$vervalue[2]); # ��ȡ��̬��ͼԴ��洢·�� , ������Ϊ���ø�ʽ

#add by hanjian 20140612
#-----------------------------------------------------------
foreach $vi(@v)
{
	@vervalue_i = &getvalue (" ",$vi); # ����ҵ����н����ҵ��ĵ�һ����ȡֵ 1����ƽ̨ 2�汾�� 3��̬��ͼ�� 4��̬��ͼ����λ�� 5����λ�� 6���Ը������ʼ���ַ
	
	if ( @vervalue_i < 4 ) # ����һ��Ӧ������ǰ5��ֵ , ����ȥ���˱���ƽֵ̨ , ���У��Ϊ4��ֵ
	{
			&printerror(0,"Wrong Format in '$vi' !\n","v");
			&command("pause");
			exit 1; # �ҵ��汾�����ڵ��е����йؼ���ȱ�����˳���������
	}
	$SnapviewP_i = &revisepath(1,$vervalue_i[2]); # ��ȡ��̬��ͼԴ��洢·�� , ������Ϊ���ø�ʽ
	push(@SnapviewP_i,$SnapviewP_i);
}

undef(@v);
#-----------------------------------------------------------

$place = "Validate_CommonPath";
print "\n$module - $place......\n";
# У��ؼ�ͨ��·���Ƿ����(�������·��,������Ϣ·��,ģ�������ļ�·��)
print "SnapviewP.commoncompP:[$SnapviewP.$commoncompP]\n";
$compileinfo_p = $SnapviewP.$compileinfo_p;
print "SnapviewP.compileinfo_p:[$compileinfo_p]\n";
###add 20130322  -----20130517 hanjian move to int.pl 
#if ((($ISM eq 1)&&($OS eq 0))||($ISM eq 0))
#{
#	print "\n- del compileinfo.....\n";
#	my $tmptmp="*.*";
#  &command("del","f",$compileinfo_p.$tmptmp);
#	}
#

if ( ! -e $SnapviewP.$commoncompP )
{
	&printerror(0,"Failed Found Path '$SnapviewP$commoncompP' !\n");
	&command("pause");
	exit 1; # �Ҳ���ͨ�ùؼ�·�����˳���������
}
if ( ! -e $SnapviewP.$compileIFP )
{
	&printerror(0,"Failed Found Path '$SnapviewP$compileIFP' !\n");
	&command("pause");
	exit 1; # �Ҳ���ͨ�ùؼ�·�����˳���������
}
if ( ! -e $SnapviewP.$moduleCFP )
{
	&printerror(0,"Failed Found Path '$SnapviewP$moduleCFP' !\n");
	&command("pause");
	exit 1; # �Ҳ���ͨ�ùؼ�·�����˳���������
}
$DynviewN = $vervalue[1]; # ��ȡ��̬��ͼ��
$ReleaseP = &revisepath(1,$vervalue[3]); # ��ȡ�汾����·�� , ������Ϊ���ø�ʽ
$TEM = $vervalue[4]; # ��ȡ�汾���Ը�����email , ������Ϊ���ø�ʽ
undef(@vervalue);
# =====================================================================================================================
# ��ȡ������ϵͳ�������� , ���ڻ�����������getenv()
use Env qw(@PATH);
@SYSPATH = @PATH;
&getenv("SVN") if ( $OS );
use Env qw(@INCLUDE);
@SYSINCLUDE = @INCLUDE;
use Env qw(@LIB);
@SYSLIB = @LIB;
&getenv("DEFENV");

# =====================================================================================================================
# ��ȡ��Ŀ�����ļ���Ϣ
sub getprjconf
{
	# input: @prjfile , $key
	# output: keyword
	my ($key) = @_;
	my @found = &getline (1,$key,@prjfile); # �ҹؼ���������
	if ( @found == 0 )
	{
		&printerror(0,"Not Config '$key' in 'main.ini' !\n",$key);
		return ""; # û���ҵ�ָ���Ĺؼ���$key���򷵻ؿ�ֵ
	}
	else
	{
		my @value = &getvalue ("=",$found[0]); # ��ȡ�ؼ��ֵ�ֵ , ����ҵ���������ҵ��ĵ�һ����ȡֵ
		if ( $value[1] eq "" )
		{
			&printerror(0,"Wrong Format '$key' in '$found[0]' !\n",$key);
			return ""; # �ҵ�ָ���Ĺؼ���$key�е���ȡֵΪ���򷵻ؿ�ֵ
		}
		else
		{
			return $value[1];
		}
	}
}
# =====================================================================================================================
# ��¼�汾��,�����main.ini��û��ָ��,Ĭ��ʹ��172.16.0.99
sub getconnect
{
	# input: 
	# output: 
	my ($user,$passwd,@addrs) = @_;
	$place = "Get_Release_Connection";
	print "\n$module - $place......\n";
	my $addr;
	foreach $addr (@addrs)
	{
		my $address = &revisepath(1,$addr);
		chop($address);
		if ( !$OS ) # Windows
		{
			my $netuse = `net use`;
			system("net use $address $passwd /user:$user 2>$vererrL") if ( $netuse !~ /$address/ );
			&printerror(0,"Failded Connect to Release Place '$address' !\n") if ( &geterror );
		}
		else # Linux
		{
			if ( !-e $address ) # �������·��������,����·��,��mount����·��
			{
				&command("mkdir",$address);
				if ( !&geterror ) # �д�����Ϣ
				{
					system("mount -t cifs -o username=$user,password=$passwd /$address $address 2>$vererrL");
					&printerror(0,"Failded Mount Release Place '/$address' to Local Dir '$address' !\n") if ( &geterror );
				}
				else
				{
					&printerror(0,"Failded Create Local Dir '$address' !\n");
				}
			}
			else # �������·������,����Ƿ��Ѿ�mount,û����mount����·��
			{
				my $mount = `mount`;
				if ( $mount !~ /\/$address\s+on\s+$address/i )
				{
					system("mount -t cifs -o username=$user,password=$passwd /$address $address 2>$vererrL");
					&printerror(0,"Failded Mount Release Place '/$address' to Local Dir '$address' !\n") if ( &geterror );
				}
			}
		}
	}
}
# ��ȡָ���Ļ������� , ������ӻ�����������sp�ű������Ӳ���У��
sub getenv
{
	# input: @env_keys
	# output: Globe Var of Build Env
	my (@envkeys) = @_;
	$place = "Get_Env";
	print "\n$module - $place......\n";
	my @envfile;
	# �򿪱��뻷�������ļ�env.ini
	if ( !open(ENV,$workpath."env.ini") )
	{
		&printerror(0,"Failed Open File '$workpath"."env.ini' !\n");
		&command("pause");
		exit 1; # �Ҳ����������������ļ����˳���������
	}
	@envfile = <ENV>;
	close(ENV);
	chomp(@envfile);
	# ��ʼѭ������ÿ���ؼ���
	my ($envkey,$env);
	foreach $envkey (@envkeys) # ���������Ļ����ؼ���
	{
		my @found = &getline(0,$envkey,@envfile);
		if ( @found == 0 ) # û���ҵ������ؼ��ֵ���
		{
			&printerror(0,"Failed Found Env Key '$envkey' in '$workpath"."env.ini' !\n");
			if (( $envkey =~ /^LinuxCC$/i ) || ( $envkey =~ /^GROUPS$/i ) || ( $envkey =~ /^DEFENV$/i ))
			{
				next; # ���������CC����������Ĭ�ϻ�������"ENV" , ���Ҳ���ʱ����
			}
			else
			{
				&command("pause");
				exit 1; # �Ҳ��������������˳���������
			}
		}
		&printerror(1,"$place : $envkey : \n");
		@found = &shiftvalue($envkey,@found); # ȥ�������ؼ��ִ�ͷ�Ĺؼ��ֺͿո�
		if ( $envkey =~ /^VC[\d\._]*$/i )
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^PATH$/i ){
					push (@PATH,$value[1]);
					$env = &command("env","PATH");
				}elsif ( $value[0] =~ /^INCLUDE$/i ){
					push (@INCLUDE,$value[1]);
					$env = &command("env","INCLUDE");
				}elsif ( $value[0] =~ /^LIB$/i ){
					push (@LIB,$value[1]);
					$env = &command("env","LIB");
				}elsif ( $value[0] =~ /^MSDevDir$/i ){
					use Env qw($MSDevDir);
					$MSDevDir = $value[1];
					$env = &command("env","MSDevDir");
				}elsif ( $value[0] =~ /^MSVCDir$/i ){
					use Env qw($MSVCDir);
					$MSVCDir = $value[1];
					$env = &command("env","MSVCDir");
				}elsif ( $value[0] =~ /^VSINSTALLDIR$/i ){
					use Env qw($VSINSTALLDIR);
					$VSINSTALLDIR = $value[1];
					$env = &command("env","VSINSTALLDIR");
				}elsif ( $value[0] =~ /^VCINSTALLDIR$/i ){
					use Env qw($VCINSTALLDIR);
					$VCINSTALLDIR = $value[1];
					$env = &command("env","VCINSTALLDIR");
				}elsif ( $value[0] =~ /^FrameworkDir$/i ){
					use Env qw($FrameworkDir);
					$FrameworkDir = $value[1];
					$env = &command("env","FrameworkDir");
				}elsif ( $value[0] =~ /^FrameworkVersion$/i ){
					use Env qw($FrameworkVersion);
					$FrameworkVersion = $value[1];
					$env = &command("env","FrameworkVersion");
				}elsif ( $value[0] =~ /^FrameworkSDKDir$/i ){
					use Env qw($FrameworkSDKDir);
					$FrameworkSDKDir = $value[1];
					$env = &command("env","FrameworkSDKDir");
				}elsif ( $value[0] =~ /^DevEnvDir$/i ){
					use Env qw($DevEnvDir);
					$DevEnvDir = $value[1];
					$env = &command("env","DevEnvDir");
				}elsif ( $value[0] =~ /^LIBPATH$/i ){
					use Env qw($LIBPATH);
					$LIBPATH = $value[1];
					$env = &command("env","LIBPATH");
				}elsif ( $value[0] =~ /^ICPP_COMPILER10$/i ){
					use Env qw($ICPP_COMPILER10);
					$ICPP_COMPILER10 = $value[1];
					$env = &command("env","ICPP_COMPILER10");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of $envkey in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^CCS[\d\._]*$/i )
		{				
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^PATH$/i ){
					push (@PATH,$value[1]);
					$env = &command("env","PATH");
				}elsif ( $value[0] =~ /^C54X_C_DIR$/i ){
					use Env qw($C54X_C_DIR);
					$C54X_C_DIR = $value[1];
					$env = &command("env","C54X_C_DIR");
				}elsif ( $value[0] =~ /^C54X_A_DIR$/i ){
					use Env qw($C54X_A_DIR);
					$C54X_A_DIR = $value[1];
					$env = &command("env","C54X_A_DIR");
				}elsif ( $value[0] =~ /^BSL5416_DIR$/i ){
					use Env qw($BSL5416_DIR);
					$BSL5416_DIR = $value[1];
					$env = &command("env","BSL5416_DIR");
				}elsif ( $value[0] =~ /^TI_DIR$/i ){
					use Env qw($TI_DIR);
					$TI_DIR = $value[1];
					$env = &command("env","TI_DIR");
				}elsif ( $value[0] =~ /^C55X_A_DIR$/i ){
					use Env qw($C55X_A_DIR);
					$C55X_A_DIR = $value[1];
					$env = &command("env","C55X_A_DIR");
				}elsif ( $value[0] =~ /^C55X_C_DIR$/i ){
					use Env qw($C55X_C_DIR);
					$C55X_C_DIR = $value[1];
					$env = &command("env","C55X_C_DIR");
				}elsif ( $value[0] =~ /^C55X_CONFIG_FILE$/i ){
					use Env qw($C55X_CONFIG_FILE);
					$C55X_CONFIG_FILE = $value[1];
					$env = &command("env","C55X_CONFIG_FILE");
				}elsif ( $value[0] =~ /^TMS470_C_DIR$/i ){
					use Env qw($TMS470_C_DIR);
					$TMS470_C_DIR = $value[1];
					$env = &command("env","TMS470_C_DIR");
				}elsif ( $value[0] =~ /^TMS470_A_DIR$/i ){
					use Env qw($TMS470_A_DIR);
					$TMS470_A_DIR = $value[1];
					$env = &command("env","TMS470_A_DIR");
				}elsif ( $value[0] =~ /^C6X_C_DIR$/i ){
					use Env qw($C6X_C_DIR);
					$C6X_C_DIR = $value[1];
					$env = &command("env","C6X_C_DIR");
				}elsif ( $value[0] =~ /^C6X_A_DIR$/i ){
					use Env qw($C6X_A_DIR);
					$C6X_A_DIR = $value[1];
					$env = &command("env","C6X_A_DIR");
				}elsif ( $value[0] =~ /^D_SRC$/i ){
					use Env qw($D_SRC);
					$D_SRC = $value[1];
					$env = &command("env","D_SRC");
				}elsif ( $value[0] =~ /^BSL6416_DIR$/i ){
					use Env qw($BSL6416_DIR);
					$BSL6416_DIR = $value[1];
					$env = &command("env","BSL6416_DIR");
				}elsif ( $value[0] =~ /^BSL6713_DIR$/i ){
					use Env qw($BSL6713_DIR);
					$BSL6713_DIR = $value[1];
					$env = &command("env","BSL6713_DIR");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of $envkey in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^ETI[\d\._]*$/i )
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^PATH$/i ){
					push (@PATH,$value[1]);
					$env = &command("env","PATH");
				}elsif ( $value[0] =~ /^EQUATOR_ROOT$/i ){
					use Env qw($EQUATOR_ROOT);
					$EQUATOR_ROOT = $value[1];
					$env = &command("env","EQUATOR_ROOT");
				}elsif ( $value[0] =~ /^ETI_TOOLKIT$/i ){
					use Env qw($ETI_TOOLKIT);
					$ETI_TOOLKIT = $value[1];
					$env = &command("env","ETI_TOOLKIT");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of '$envkey' in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^Tornado[\d\._]*$/i )
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^PATH$/i ){
					push (@PATH,$value[1]);
					$env = &command("env","PATH");
				}elsif ( $value[0] =~ /^WIND_BASE$/i ){
					use Env qw($WIND_BASE);
					$WIND_BASE = $value[1];
					$env = &command("env","WIND_BASE");
				}elsif ( $value[0] =~ /^WIND_HOST_TYPE$/i ){
					use Env qw($WIND_HOST_TYPE);
					$WIND_HOST_TYPE = $value[1];
					$env = &command("env","WIND_HOST_TYPE");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of $envkey in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^InstallShield[\d\._]*$/i )
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^InstallShield12Build$/i ){
					use Env qw($InstallShield12Build);
					$InstallShield12Build = $value[1];
					$env = &command("env","InstallShield12Build");
				}elsif ( $value[0] =~ /^InstallShield12Script$/i ){
					use Env qw($InstallShield12Script);
					$InstallShield12Script = $value[1];
					$env = &command("env","InstallShield12Script");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of $envkey in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^linuxintel[\d\._]*$/i )
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^PATH$/i ){
					push (@PATH,$value[1]);
					$env = &command("env","PATH");
				}elsif ( $value[0] =~ /^INCLUDE$/i ){
					push (@INCLUDE,$value[1]);
					$env = &command("env","INCLUDE");
				}elsif ( $value[0] =~ /^LIB$/i ){
					push (@LIB,$value[1]);
					$env = &command("env","LIB");
				}elsif ( $value[0] =~ /^LIBRARY_PATH$/i ){
					use Env qw($LIBRARY_PATH);
					$LIBRARY_PATH = $value[1];
					$env = &command("env","LIBRARY_PATH");
				}elsif ( $value[0] =~ /^LD_LIBRARY_PATH$/i ){
					use Env qw($LD_LIBRARY_PATH);
					$LD_LIBRARY_PATH = $value[1];
					$env = &command("env","LD_LIBRARY_PATH");
				}elsif ( $value[0] =~ /^DYLD_LIBRARY_PATH$/i ){
					use Env qw($DYLD_LIBRARY_PATH);
					$DYLD_LIBRARY_PATH = $value[1];
					$env = &command("env","DYLD_LIBRARY_PATH");
				}elsif ( $value[0] =~ /^NLSPATH$/i ){
					use Env qw($NLSPATH);
					$NLSPATH = $value[1];
					$env = &command("env","NLSPATH");
				}elsif ( $value[0] =~ /^MANPATH$/i ){
					use Env qw($MANPATH);
					$MANPATH = $value[1];
					$env = &command("env","MANPATH");
				}elsif ( $value[0] =~ /^INTEL_LICENSE_FILE$/i ){
					use Env qw($INTEL_LICENSE_FILE);
					$INTEL_LICENSE_FILE = $value[1];
					$env = &command("env","INTEL_LICENSE_FILE");
				}elsif ( $value[0] =~ /^CPATH$/i ){
					use Env qw($CPATH);
					$CPATH = $value[1];
					$env = &command("env","CPATH");
				}elsif ( $value[0] =~ /^FPATH$/i ){
					use Env qw($FPATH);
					$FPATH = $value[1];
					$env = &command("env","FPATH");
				}elsif ( $value[0] =~ /^IPPROOT$/i ){
					use Env qw($IPPROOT);
					$IPPROOT = $value[1];
					$env = &command("env","IPPROOT");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of $envkey in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^sign[\d\._]*$/i )
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^PATH$/i ){
					push (@PATH,$value[1]);
					$env = &command("env","PATH");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of $envkey in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^GTK[\d\._]*$/i )
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^INCLUDE$/i ){
					push (@INCLUDE,$value[1]);
					$env = &command("env","INCLUDE");
				}elsif ( $value[0] =~ /^LIB$/i ){
					push (@LIB,$value[1]);
					$env = &command("env","LIB");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of $envkey in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif (( $envkey =~ /^SVN[\d\._]*$/i ) ||( $envkey =~ /^PPC[\d\._]*$/i ) || ( $envkey =~ /^ARM[\d\._]*$/i ) || ( $envkey =~ /^EQT[\d\._]*$/i ) || ( $envkey =~ /^DAVINCI[\d\._]*$/i ))
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^PATH$/i ){
					push (@PATH,$value[1]);
					$env = &command("env","PATH");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of '$envkey' in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^GROUPS[\d\._]*$/i )
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^CLEARCASE_GROUPS$/i ){
					use Env qw($CLEARCASE_GROUPS);
					$CLEARCASE_GROUPS = $value[1] if ( $value[1] ne "" );
					$env = &command("env","CLEARCASE_GROUPS");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of '$envkey' in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^LinuxCC[\d\._]*$/i )
		{
			foreach $f (@found) # ���д������ؼ��ֵ���
			{
				my @value = &getvalue("=",$f); # ���뻷������value[0]�뻷������ֵvalue[1]
				if ( $value[0] =~ /^PATH$/i ){
					my @part = &getvalue(":",$value[1]);
					foreach $p (@part)
					{
						my @fd = grep(/$p/,@SYSPATH);
						if ( @fd == 0 )
						{
							push (@PATH,$p);
							push (@SYSPATH,$p);
						}
					}
					undef(@fd);
					undef($p);
					undef(@part);
					$env = &command("env","PATH");
				}else{
					&printerror(0,"Unknow Key Var '$value[0]' of $envkey in '$workpath"."env.ini' !\n");
				}
				&printerror(1,$env);
			}
		}
		elsif ( $envkey =~ /^DEFENV$/i )
		{
			@f = &getline(1,$OSstring,@found);
			if ( @f == 0 )
			{
				&printerror(0,"Failed Found $OSstring Default Env Key in '$workpath"."env.ini' !\n");
			}
			else
			{
				my @value = &getvalue("=",$f[0]); # ���뻷������value[0]�뻷������ֵvalue[1]
				$defenv = $value[1];
				$defenv = "-" if ( $defenv eq "" ); # ���Ĭ�ϻ�������Ϊ��,�����module������������
				&printerror(1,"$OSstring Default Env Key : $defenv\n");
			}
			undef(@f);
		}
		else
		{
			&printerror(0,"UnPreDefine Key Var '$value[0]' of '$envkey' in '$workpath"."common.pl' !\n");
		}
	}
}
# =====================================================================================================================
# ����ģ�����֮ǰ , �����ڱ��뻷���ı�Ҫ���� , ���紴��log�ļ��ͱ�����Ϣ�ļ���
sub preprocess
{
	# input: $options
	# output: none
	my ($options) = @_;
	# ������� , ����ģ�����֮ǰ����common��ָ����Ŀ¼
	if ( $options =~ /u/ )
	{
		$place = "Update_Common_SourceCode";
		print "\n$module - $place......\n";

		if ( $OS )
		{
			&module("u","-","common");
		}
		else
		{
			&module("u","GROUPS","common");
		}
	}
	# ��main.ini�ļ���ȡ��Ҫ��Ϣ
	if ( open(PROJECT,$workpath."main.ini") )
	{
		$place = "Get_Project_Config";
		print "\n$module - $place......\n";
		@prjfile = <PROJECT>;
		close(PROJECT);
		chomp(@prjfile);
	}
	else
	{
		&printerror(0,"Failed Open File '$workpath"."main.ini' !\n");
	}
	# ���У�� , ��Ԥ��������ģ��У���ļ����Ϊͨ�� , ��ģ����ù��������в�ͨ����ģ������0 , �����ʼ�����
	if ( $options =~ /c/ )
	{
		$place = "Create_NoPassFile_Log";
		print "\n$module - $place......\n";
		my $checkreadme = &getprjconf("checkreadme");
		$CRM = 0; # ���û���д��checkreadme��ϢתΪboolֵ�������ж�
		$CRM = 1 if (( $checkreadme eq "" ) || ( $checkreadme eq "yes" ) || ( $checkreadme eq "y" )); # ������дֵΪ�ջ�������"yes"ʱ$CRM��Ϊ��
		undef($checkreadme);
		$allcheckfileS = 1;
		if ( $options !~ /f/ )
		{
			my $releasekreadme = &getprjconf("releasereadme");
			$RRM = 0; # ���û���д��releasekreadme��ϢתΪboolֵ�������ж�
			$RRM = 1 if (( $releasekreadme eq "" ) || ( $releasekreadme eq "yes" ) || ( $releasekreadme eq "y" )); # ������дֵΪ�ջ�������"yes"ʱ$RRM��Ϊ��
			undef($releasekreadme);
			if ( $CRM )
			{
				$place = "Create_AllReadme_Log";
				print "\n$module - $place......\n";
				if ( open (ALLREADME,">$verworkP"."AllReadme.txt") ) # ��¼У��δͨ��readme����ʱ�ļ� , ��nopassreadme.log�ļ����
				{
					&getallmodules;
					#print ALLREADME "UCMproject : $UCMprj\nVersion : $version\nModules : "."@allmodules"."\nCheck Readme Time : $builddate\n";
					close (ALLREADME);
					undef(@allmodules);
				}
				else
				{
					 &printerror(0,"Failed Open File '$verworkP"."AllReadme.txt' !\n");
				}
			}
		}
	}
	# �������,���ȡ����·��
	if ( $options =~ /r/ )
	{
		# ��ȡ��¼��ָ���İ汾�������������Ϣ
		my $connect_address = &getprjconf("connect_addrs");
		my @connect_addrs = &getvalue(",",$connect_address);
		undef($connect_address);
		my $connect_user = &getprjconf("connect_user");
		my $connect_passwd = &getprjconf("connect_passwd");
		# ��������
		&getconnect($connect_user,$connect_passwd,@connect_addrs) if (( $connect_user ne "" ) && ( @connect_addrs > 0 ));
		undef(@connect_addrs);
		undef($connect_user);
		undef($connect_passwd);
		my $TSstr = &getprjconf("buildtime_span");
		$TS = 180*60; # Ĭ��180���� , ������
		if (( $TSstr =~ /^\d+$/ ) && ( $TSstr <= 1440 )) # ���1440���� , ��һ��
		{
			$TS = $TSstr*60; # ������
		}
		my $winfirst = &getprjconf("windowsfirst");
		$WF = 0; # ���û���д��releasekreadme��ϢתΪboolֵ�������ж�
		$WF = 1 if (( $winfirst eq "" ) || ( $winfirst eq "yes" ) || ( $winfirst eq "y" )); # ������дֵΪ�ջ�������"yes"ʱ$WF��Ϊ��
	}
	if ( $options =~ /n/ )
	{
		my $sendmail = &getprjconf("sendmail");
		$MAIL = 0; # ���û���д��sendmail��ϢתΪboolֵ�������ж�
		$MAIL = 1 if (( $sendmail eq "" ) || ( $sendmail eq "yes" ) || ( $sendmail eq "y" )); # ������дֵΪ�ջ�������"yes"ʱ$MAIL��Ϊ��
		undef($sendmail);
		if ( $MAIL )
		{
			$PMM = &getprjconf("PMmail");
			$CMOM = &getprjconf("CMOmail");
			$CMOM = "sqlmail\@kdcrd.com" if ( $CMOM eq "" );
			$SMTP = &getprjconf("MAIL_SMTP_IP"); # �����ʼ�֪ͨ��SMTP����
			$SMTP = "10.5.0.54" if ( $SMTP eq "" );
			$mailfrom = &getprjconf("MAIL_FROM"); # �����ʼ�֪ͨ�ķ���������
			$mailfrom = "sqlmail\@kedacom.com" if ( $mailfrom eq "" );
		}
	}
	undef(@prjfile);
}
# ����ģ�����֮�� , �����ڱ��뻷���ı�Ҫ���� , ���緢�����뱨��,����readme,����������Ϣ��
sub afterprocess
{
	# input: $options
	# output: none
	my ($options) = @_;
	# ������� , �򷢲�������Ϣ��common��ָ����Ŀ¼���ļ�
	if ( $options =~ /r/ )
	{
		# ����Ǽ��ɱ��� , ����common��ָ����Ŀ¼���ļ�
		if ( $options =~ /i/ )
		{
			$place = "Release_Common_Files_Dirs";
			print "\n$module - $place......\n";
			&module("r","-","common");
		}
		# �����Ҫ����readme , �򷢲�AllReadme.txt�ļ�
		if ( $RRM )
		{
			$place = "Release_AllReadme";
			print "\n$module - $place......\n";
			my $WreadmeFRP = &revisepath(1,$RP."readme"); # ����·��Ϊ���ø�ʽ
			&command("copy","f",$verworkP."AllReadme.txt",$WreadmeFRP);
			&geterror; # �д�����Ϣ��д��BuildError.log
		}
	}
	# ������� , ����������Ϣ�ļ�
	if ( $options =~ /b/ )
	{
		$place = "Make_BuildInfo";
		print "\n$module - $place......\n";
		# �򿪱�����ϢBuildInfo.txt
		if ( open(BUILDINFO,">$verworkP"."BuildInfo_".$OSstring."_".$builddatetime.".txt") )
		{
			my $currenttime = &getdatetime;
			print BUILDINFO "Product Line : $proline\nProject : $UCMprj\nVersion : $version\nBuild Server : $BuildServer\nAutoBuild Script Path : $workpath\nBuildTime : $builddatetime ~ $currenttime\n";
			&getallmodules;
			print BUILDINFO "Modules : "."@allmodules"."\n";
			undef(@allmodules);
			print BUILDINFO "ReleasPlace : $RP\n" if ( $options =~ /r/ );
			# Baselineд�������Ϣ  ###hanjian 20120817 delete 
###			if ( open(BASELINE,"all-code_".$builddatetime."_update.log") )
###			{
###				@blfile = <BASELINE>;
###				close(BASELINE);
###				print BUILDINFO ("\n============================= Stream update Info =============================\n"."@blfile"."\n");
###				undef(@blfile);
###			}
###			else
###			{
###				$place = "Read_updateLog";
###				&printerror(0,"Failed Open File '$verworkP"."baseline.log' !\n");
###			}
			# ����ʱ��д�������Ϣ
			if ( open(TIME,$verworkP."buildtime.log") )
			{
				@timefile = <TIME>;
				close(TIME);
				print BUILDINFO ("\n============================= Module Build Info =============================\n"."@timefile"."\n");
				undef(@timefile);
			}
			else
			{
				$place = "Read_BuildTimeLog";
				&printerror(0,"Failed Open File '$verworkP"."buildtime.log' !\n");
			}
			# У���ļ����д�������Ϣ
			if ( $options =~ /c/ )
			{
				if ( open(CHECK,$verworkP."checkfile.log") )
				{
					@checkfile = <CHECK>;
					close(CHECK);
					print BUILDINFO ("\n============================= Modules Check Info =============================\n"."@checkfile"."\n");
					undef(@checkfile);
				}
				else
				{
					$place = "Read_CheckFileLog";
					&printerror(0,"Failed Open File '$verworkP"."checkfile.log' !\n");
				}
			}
			print BUILDINFO "\n\nEND\n";
			close(BUILDINFO);
			# ����������Ϣ�ļ�
			if ( $options =~ /r/ )
			{
				$place = "Release_BuildInfo";
				print "\n$module - $place......\n";
				&command("copy","f",$verworkP."BuildInfo_".$OSstring."_".$builddatetime.".txt",$RP); # ����BuildInfo
				
				#####hanjian 20120810 
				
				&command("copy","f",$verworkP."all-code_".$OSstring."_".$builddatetime."_update.log",$RP); # �����������ʱ��ȫ������svn update�Ľ����¼�ļ�
				
				&geterror;
			}
		}
		else
		{
			$place = "Create_BuildInfo";
			&printerror(0,"Failed Open File '$verworkP"."buildinfo_$OSstring_$builddatetime.txt' !\n");
		}
	}
	# �������֪ͨ���� , ģ�������ɺ��ṩ����״̬����
	if (( $options =~ /n/ ) && $MAIL )
	{
		$place = "Build_Status_Report";
		print "\n$module - $place......\n";
		if ( $options =~ /i/ )
		{
			&notify(0,1);
		}
		else
		{
			&notify(0,0);
		}
	}
}
# ģ����������̣������ⲿ����
sub module
{
	# parameter usage -----
	# 0 moduleoptions : not necessary
	# 0.0 defause none : whole build process , check file , notify bases spbuild rule
	# 0.1 -u : update src code
	# 0.2 -b : build(compile)
	# 0.30 -c : check file and readme
	# 0.31 -cf : check file
	# 0.4 -r : release
	# 0.50 -n : notify rules , must be used with -c that means check and notify
	# 0.51 -i : handle bases on intbuild rule or on spbuild rule
	# 1 envkeylist : the env keys of the project , necessary but allow none , more than one keys seperate by ","
	# 2 modules : modules of the version , necessary
	# input : $moduleoptions , $moduleenvs , @modules
	# output : BuildError.txt
	my ($moduleoptions,$moduleenvs,@modules) = @_;
	$module = "";
	&printerror(1,"\n~~~~~~~~~~~~~~~ Modules Process Start ~~~~~~~~~~~~~~\n"); # �������ֶ�ε���module�Ĵ�ӡ��Ϣ
	&printerror(1,"ModulesOptions : $moduleoptions\nEnv : $moduleenvs\nModules : "."@modules"."\n");
	# ��ȡdemo�ļ���Ϣ������module���ô���ʱ����ȷ����
#	open(DEMO,$SnapviewP.$moduleCFP."demo.ini");
#	local @demofile = <DEMO>; # ��ȡ�ļ�����
#	close(DEMO);
#	chomp(@demofile); # ȥ�����з�
	# =====================================================================================================================
	# ������������ؼ��ֲ��� , ���û�������
	if ( $moduleenvs !~ /^-*$/ )
	{
		my @envs = &getvalue(",",$moduleenvs);
		if ( $moduleoptions =~ /b/ )
		{
			# �����һ�λ�ȡgetenv�Ա��뻷���ĸı�
			undef(@PATH);
			@PATH = @SYSPATH;
			undef(@INCLUDE);
			@INCLUDE = @SYSINCLUDE;
			undef(@LIB);
			@LIB = @SYSLIB;
		}
		&getenv(@envs);
	}
	# =====================================================================================================================
	# ��ģ�����ε���
	local ($WmoduleCF,@modufile); # $module�Ѿ�����Ϊȫ��
	for ( my $i = 0 ; $i < @modules ; $i ++ )
	{
		$module = $modules[$i];
		# ��ȡģ�������ļ�·��
		$WmoduleCF = $SnapviewP.$moduleCFP.$module.".ini";
		# ��ģ�������ļ���Ϣ
		if ( open(MODULE,$WmoduleCF) )
		{
			@modufile = <MODULE>;
			close(MODULE);
			chomp(@modufile);
			# ��ÿ��ģ�����ģ��������
			&process($moduleoptions);
			undef(@modufile);
		}
		else
		{
			&printerror(0,"Failed Open Module Config File '$WmoduleCF' !\n");
			&printerror(0,"Skip Module Build !\n");
			next; # �򲻿�ģ�������ļ���������ģ��������
		}
	}
	$module = "";
	&printerror(1,"\n--------------- Modules Process End ---------------\n"); # �������ֶ�ε���module�Ĵ�ӡ��Ϣ
}
# ����ģ��������̶���ģ��������
sub process
{
	# input: $options , $CRM , $RRM
	# output: none
	# �����û��Ա�����̵�ѡ���ģ�������ļ���ȡ���α������������������Ϣ
	my ($options) = @_;
	local ($srccodePs,$owner,$ownerM);
	&getmoduconf_s if (( $options =~ /u/ ) || ( $options =~ /c/ ) || ( $options =~ /n/ ));
	local ($compileFP,$compileFN,$readmeFP,$readmeFN);
	&getmoduconf_b if (( $options =~ /b/ ) || (( $options =~ /c/ ) && ( $options !~ /f/ ) && ( $CRM || $RRM )) || ( $options =~ /r/ ));
	local (@checkFs,@releaseFs); # ���ڴ��У���ļ����ļ�����·��/�����ļ����ļ�����·���ͷ���·�� ; ��getmoduconf_c��ֵ��del/checkfile/releasefilesʹ��
	&getmoduconf_c if (( $options =~ /b/ ) || ( $options =~ /c/ ) || ( $options =~ /r/ ));
	local (@releaseDs); # ���ڴ�ŷ���Ŀ¼��Ŀ¼����·�� ; ��getmoduconf_r��ֵ��releasefilesʹ��
	&getmoduconf_r if ( $options =~ /r/ );
	# �����û��Ա�����̵�ѡ������ӳ���
	&del if ( $options =~ /b/ );
	local $updateS = 1;
	if ( $options =~ /u/ )
	{
		&update;
		&disreadonly; # updateԴ���ȥ��Դ���ֻ������
	}
#	print "updateS : $updateS\n";
	local $compileS = 1;
	&compile if ( $options =~ /b/ );
	# ���ڷ����ʼ��ж� , У����Ϊ0ʱ�����ʼ� , ����У�鲻ͨ��ʱ�����ʼ� , ��main.ini�����ò�У���δ����У����ϢҲ�������ʼ�
	local $checkfileS = 1;
	local $checkreadmeS = 1;
	local ($CFpassinfo,$RMpassinfo);
	if ( $options =~ /c/ )
	{
		&checkfile;
		# �ӹ���ѡ�����Ϊcfʱ��У��readme ; ��Ŀ�����ļ���������ҪУ��readmeʱ��У��readme
		####&checkreadme if ( $CRM && ( $options !~ /f/ )); ###20120822 delete by hanjian ��ʱע��
	}
#	print "checkfileS : $checkfileS\ncheckreadmeS : $checkreadmeS\nCFpassinfo : $CFpassinfo\nRMpassinfo : $RMpassinfo\n";
	if ( $options =~ /r/ )
	{
		&releasefiles;
		# ��Ŀ�����ļ���������Ҫ����readmeʱ�ŷ���readme ; У���ļ����ͨ��ʱ�ŷ���readme
		&releasereadme if ( $RRM && ( $checkfileS == 1 ));
		&releasecompileinfo;
		&releaseupdatelog if ( $options =~ /u/ );
	}
	if (( $options =~ /n/ ) && $MAIL )
	{
		if ( !$checkfileS || ( $CRM && !$checkreadmeS )) # ����ģ���֪ͨ���� , ��У�鲻ͨ��ʱ��֪ͨ
		{
			if ( $options =~ /i/ )
			{
				&notify(1,1);
			}
			else
			{
				&notify(1,0);
			}
		}
	}
}
# =====================================================================================================================
# ��ȡģ�������ļ���Ϣ
sub getmoduconf
{
	# input: $keyword , @modufile
	# output: lines found in @modufile begin with the keyword
	my ($key) = @_;
	$place = "Get_Module_Config_$key";
	print "\n$module - $place......\n";
	my @l;
	if ($OS)
	{
		@l = &getline (0,$key."l",@modufile); # linux����ϵͳ�ڹؼ��ֺ��"l"����
	}
	else
	{
		@l = &getline (0,$key."w",@modufile); # windows����ϵͳ�ڹؼ��ֺ��"w"����
	}
	&printerror(0,"Not Config '$key' in '$WmoduleCF' !\n",$key) if ( @l == 0 );
	return @l;
}
# ��ȡģ��Դ����Ϣ
sub getmoduconf_s
{
	# input: none
	# output: $srccodePs , $owner , $ownerM
	my @s = &getmoduconf("s"); # ��ģ�������ļ���ȡ's'�ؼ�����
	if ( @s != 0 )
	{
		my @value = &getvalue (" ",$s[0]); # �Ӹ��л�ȡ���йؼ�ֵ 1Դ��·���б� 2������ 3�ʼ���ַ
		if ( $value[1] =~ /^-*$/ ) # ���Դ��·���б�Ϊ�� , ����"-"����Ϊ��
		{
			$srccodePs = "";
			&printerror(0,"Wrong Format of Source Code Path in '$s[0]' !\n","s");
		}
		else
		{
			$srccodePs = $value[1];
		}
		if ( $value[2] =~ /^-*$/ )
		{
			$owner = "";
		}
		else
		{
			$owner = $value[2];
		}
		if (( $value[3] =~ /^-*$/ ) || ( $value[3] !~ /@/ ))
		{
			$ownerM = "sqlmail\@kedacom.com";
		}
		else
		{
			$ownerM = $value[3];
		}
	}
	else
	{
		$srccodePs = "";
	}
#	print "srccodePs : $srccodePs\nowner : $owner\nownerM : $ownerM\n";
}
# ��ȡģ������readme��Ϣ
sub getmoduconf_b
{
	# input: none
	# output: $compileFP , $compileFN , $readmeFP , $readmeFN
	my @b = &getmoduconf("b"); # ��ģ�������ļ���ȡ'b'�ؼ�����
	if ( @b == 0 ) # û�ҵ�ƥ�����
	{
		$compileFN = "";
		$readmeFN = "";
	}
	else
	{
		my @value = &getvalue (" ",$b[0]); # �Ӹ��л�ȡ���йؼ�ֵ 1compile�ļ�·�� 2compile�ļ����� 3readme�ļ�·�� 4readme�ļ�����
		if (( $value[1] =~ /^-*$/ ) || ( $value[2] =~ /^-*$/ )) # ���compile�ļ�·����compile�ļ����ƶ���Ϊ�� , ��ֵ����Ч
		{
			$compileFP = "";
			$compileFN = "";
			&printerror(0,"Wrong Format of Compile File in '$b[0]' !\n","b");
		}
		else
		{
			$compileFP = &revisepath(0,$value[1]); # ����·��Ϊ���ø�ʽ
			$compileFN = $value[2];
		}
		if ( $value[4] =~ /^-*$/ )
		{
			$readmeFP = "";
			$readmeFN = "";
			&printerror(0,"Wrong Format of Readme File in '$b[0]' !\n","b");
		}
		else
		{
			if ( $value[3] =~ /^-*$/ ) # ���compile�ļ�·����compile�ļ����ƶ���Ϊ�� , ��ֵ����Ч
			{
				if ( $compileFP eq "" ) # ��compile�ļ�·����Ϊ�� , ����Ϊreadme�ļ�·����compile�ļ�·��
				{
					$readmeFP = "";
					$readmeFN = "";
					&printerror(0,"Wrong Format of Readme File in '$b[0]' !\n","b");
				}
				else
				{
					$readmeFP = $compileFP;
					$readmeFN = $value[4];
				}
			}
			else # ���readme�ļ�·��Ϊ�� , 
			{
				$readmeFP = &revisepath(0,$value[3]); # ����·��Ϊ���ø�ʽ
				$readmeFN = $value[4];
			}
		}
	}
#	print "compileFP : $compileFP\ncompileFN : $compileFN\nreadmeFP : $readmeFP\nreadmeFN : $readmeFN\n";
}
# ��ȡģ��У��ͷ����ļ���Ϣ
sub getmoduconf_c
{	# input: none
	# output: @checkFs , @releaseFs
	my @c = &getmoduconf("c"); # ��ģ�������ļ���ȡ'c'�ؼ�����
	if ( @c != 0 ) # ��ģ�������ļ��ҵ���'c'�ؼ�����
	{
		my ($c,@checkFNs,$checkFN,$checkFP,$FRP,@FRPs);
		foreach $c (@c) # ��ÿһ�л�ȡ��Ϣ
		{
			my @value = &getvalue (" ",$c); # �Ӹ��л�ȡ���йؼ�ֵ 1У���ļ������б� 2�ļ�·�� 3����·���б�
			if ( @value < 3 ) # ����ؼ�ֵΪǰ3��
			{
				&printerror(0,"Wrong Format in line '$c[0]' !\n","c");
				next; # ����ؼ�ֵȱ������������
			}
			@checkFNs = &getvalue(",",$value[1]); # ��У���ļ������б�תΪ����
#			@checkFNs = split(/,/,$value[1]); # ��У���ļ������б�תΪ����
			$checkFP = &revisepath(0,$value[2]); # ����·��Ϊ���ø�ʽ
			if ( $value[3] =~ /^-*$/ ) # �Ӹ��л�ȡ��'����·���б�'Ϊ�� , ����"-"����Ϊ��
			{
				foreach $checkFN (@checkFNs)
				{
					# ����checkF��ʽΪ : 0�ļ����� 1�ļ�·��
					push (@checkFs,$checkFN." ".$checkFP);
#					print "checkF : $checkFN $checkFP\n";
				}
			}
			else
			{
				foreach $checkFN (@checkFNs)
				{
					# ����checkF��ʽΪ : 0�ļ����� 1�ļ�·�� 2����·���б�
					push (@checkFs,$checkFN." ".$checkFP." ".$value[3]);
					# ����releaseF��ʽΪ : 0�ļ����� 1�ļ�·�� 2����·���б�
					push (@releaseFs,$checkFN." ".$checkFP." ".$value[3]);
#					print "checkF/releaseF : $checkFN $checkFP $value[3]\n";
				}
			}
		}
		&printerror(0,"Failed Get Release Files Config in '$WmoduleCF' !\n") if ( @releaseFs == 0 );
	}
	else
	{
		undef(@checkFs);
		undef(@releaseFs);
	}
}
# ��ȡģ�鷢��Ŀ¼��Ϣ
sub getmoduconf_r
{
	# input: none
	# output: @releaseDs
	my @r = &getmoduconf("r"); # ��ģ�������ļ���ȡ'r'�ؼ�����
	if ( @r != 0 ) # ��ģ�������ļ��ҵ���'r'�ؼ�����
	{
		my ($r,@RDNs,$RDN,$RDP);
		foreach $r (@r) # ��ÿһ�л�ȡ��Ϣ
		{
			my @value = &getvalue (" ",$r); # �Ӹ��л�ȡ���йؼ�ֵ 1�����ļ��������б� 2�ļ���·�� 3����·���б�
			if ( @value < 4 ) # ����ؼ�ֵΪǰ4��
			{
				&printerror(0,"Wrong Format in '$r[0]' !\n","r");
				next; # ����ؼ�ֵȱ������������
			}
			@RDNs = &getvalue(",",$value[1]); # ��У���ļ������б�תΪ����
#			@RDNs = split(/,/,$value[1]); # ��У���ļ������б�תΪ����
			foreach $RDN (@RDNs)
			{
				# ����releaseDs��ʽΪ : 0Ŀ¼���� 1Ŀ¼·�� 2����·���б�
				push (@releaseDs,$RDN." ".$value[2]." ".$value[3]);
#				print "releaseD : $RDN $RDP $value[3]\n";
			}
		}
		&printerror(0,"Failed Get Release Dirs Config in '$WmoduleCF' !\n") if ( @releaseDs == 0 );
	}
	else
	{
		undef(@releaseDs);
	}
}
# =====================================================================================================================
#add by hanjian 20120824

#sub deletecompileinfo
#{
#	print"delete compileinfo\n";
	
#	$compilePPP = &getprjconf("compileinfo_path");
	
#	$compilePPP = &revisepath(1,$compilePPP);
#	my $compileinfopath=$SnapviewP.$compilePPP."*.*";
#	print "\n!!!!!compile info path: $compileinfopath\n";
#	&command("del","f",$compileinfopath);
	
#}
# =====================================================================================================================
# ģ�����'ɾ��'�ӹ���
sub del
{
	# input: $SnapviewP , $commoncompP , @checkFs
	# output: none
	$place = "Delete_CheckFiles";
	print "\n$module - $place......\n";
	# ɾ��У���ļ�
	if ( @checkFs != 0 ) # �������ļ��л�ȡ��У���ļ���Ϣ��Ϊ��
	{
		my ($checkF,$WcheckF,$WcheckFs);
		foreach $checkF (@checkFs) # ��ȡÿһ��У���ļ�����Ϣ
		{
			my @value = &getvalue(" ",$checkF); # ��ȡ��У���ļ���Ϣ 0�ļ����� 1�ļ�·�� 2����·���б�
			$value[0] =~ s/(y{2}|y{4})mmdd/\*/ig; # chenhuiren 090610 
			$WcheckF = $SnapviewP.$commoncompP.$value[1].$value[0]; # ����·�����ļ���
#			print "WcheckF : $WcheckF\n";
			$WcheckFs = $WcheckFs.$WcheckF." "; # �����У���ļ��ÿո������Ϊdel�������һ��ִ��
		}
		&command("del","f",$WcheckFs); # ����ɾ��������������Ϣ��error.log��
	}
	&geterror; # �д�����Ϣ��д��BuildError.log
}

# ģ�����'����Դ�ļ�'�ӹ���
sub update
{
	# input: $SnapviewP , $srccodePs , $module , $builddatetime
	# output: update log
	$place = "Update_SourceCode";
	print "\n$module - $place......\n";
	if ( $srccodePs ne "" )
	{
		my ($srccodeP,$WsrccodeP,$WsrccodePs);
		&cleanerror;
		my @srccodeP = &getvalue(",",$srccodePs); # �����Դ��·���ָ������
#		my @srccodeP = split(/,/,$srccodePs); # �����Դ��·���ָ������
		#####my $updateLFN = $module."_".$builddatetime.".updt"; # �������Դ�����־�ļ�����
		my $updateLFN = $module."_".$builddatetime."_update.log"; # �������Դ�����־�ļ�����  modify by hanjian 20120803 ;modify by hanjian 20120810
		
		foreach $srccodeP (@srccodeP)
		{
			$srccodeP = &revisepath(2,$srccodeP); # ����·��Ϊ���Ը�ʽ
			$WsrccodeP = $SnapviewP.$srccodeP; # ƴ�ӳ�����·��
#			print "WsrccodeP : $WsrccodeP\n";
			$WsrccodePs = $WsrccodePs.$WsrccodeP." "; # �����Դ��·���ÿո������Ϊupdate�������һ��ִ��
		}
		&command("update",$WsrccodePs,$verworkP.$updateLFN); # ���ø���Դ��������������Ϣ��error.log��
		if ( &geterror ) # �д�����Ϣ
		{
			$updateS = 0;
			&printerror(0,"@errors");
		}
	}
	else
	{
		$updateS = 0;
		&printerror(0,"Failed Get SourceCode Path Config !\n");
		&printerror(0,"Skip Update Process !\n");
	}
}


# ȥ��Դ��ֻ������
sub disreadonly
{
	# input: $SnapviewP , $srccodePs , $module , $builddatetime
	# output: update log
	$place = "DisReadonly_SourceCode";
	print "\n$module - $place......\n";
	if (( $srccodePs ne "" ) && $updateS )
	{
		my ($srccodeP,$WsrccodeP);
		my @srccodeP = &getvalue(",",$srccodePs); # �����Դ��·���ָ������
#		my @srccodeP = split(/,/,$srccodePs); # �����Դ��·���ָ������
		my $updateLFN = $module."_".$builddatetime.".updt"; # �������Դ�����־�ļ�����
		foreach $srccodeP (@srccodeP)
		{
			$srccodeP = &revisepath(0,$srccodeP); # ����·��Ϊ���Ը�ʽ
			$WsrccodeP = $SnapviewP.$srccodeP; # ƴ�ӳ�����·��
#			print "WsrccodeP : $WsrccodeP\n";
			&command("disreadonly",$WsrccodeP); # ���ø���Դ��������������Ϣ��error.log��
		}
		&geterror; # �д�����Ϣ��д��BuildError.log
	}
}
# ģ�����'����'�ӹ���
sub compile
{
	# input: $SnapviewP , $compileFP , $compileFN , $module , $OSstring , $workpath
	# output: $module_$OSstring_cmo.txt
	$place = "Compile";
	print "\n$module - $place......\n";
	if (( $compileFN ne "" ) && $updateS )
	{
		my $WcompileFP = $SnapviewP.$compileFP;
#		print "WcompileFP : $WcompileFP\n";
		my $WcompileIF = $SnapviewP.$compileIFP.$module."_".$OSstring.".txt"; # ������CMO��ȡ�ı�����Ϣ�ļ�����
		if ( chdir($WcompileFP) ) # �������·��
		{
			my $currenttime = &getdatetime;
			&writelog($verworkP."buildtime.log","$module Compile Time : $currenttime"); # �������ļ�У����д����־�ļ�
#			print BUILDINFO ("\n$module Compile Time : $currenttime"); # ��������ʼʱ��д�������Ϣ�ļ�
			&command("exe",$compileFN,$WcompileIF); # ���ñ���������������Ϣ��CMO�ı�����Ϣ�ļ���
			$currenttime = &getdatetime;
			&writelog($verworkP."buildtime.log","~ $currenttime\n"); # �������ļ�У����д����־�ļ�
#			print BUILDINFO ("~ $currenttime\n"); # ��������ֹʱ��д�������Ϣ�ļ�
			&printerror(0,"Failed Access Work Path '$workpath' !\n") if ( !chdir($workpath) ); # ���ص�ǰ����·��
		}
		else
		{
			$compileS = 0;
			&printerror(0,"Failed Access Compile Path '$WcompileFP' !\n");
			&printerror(0,"Skip Compile Process !\n");
		}
	}
	else
	{
		$compileS = 0;
		&printerror(0,"Failed in Update Process !\n") if ( !$updateS );
		&printerror(0,"Failed Get Compile File Config !\n") if ( $compileFN ne "" );
		&printerror(0,"Skip Compile Process !\n");
	}
}
# ģ�����'У���ļ�'�ӹ���
sub checkfile
{
	# input: @checkFs
	# output: $checkfileS , checkfile.log
	$place = "Check_File";
	print "\n$module - $place......\n";
	my ($errorinfo,$passinfo,$nopassinfo);
	if ( @checkFs != 0 ) # ��ģ�������ļ���ȡ��У���ļ���Ϣ��Ϊ��ʱ
	{
		my ($checkF,$WcheckF);
		undef(@releaseFs); # ����Ѷ���ķ����ļ���У��ͨ�����ļ��ŷ���
		my $date4 = &getdate; # ��ȡ��ǰ����yyyymmdd # chenhuiren 090610 
		my $date2 = substr($date4,2);# ��ȡ��ǰ����yymmdd # chenhuiren 090610
		# chenhuiren 090610 start
		foreach $checkF (@checkFs)
		{
			$checkF =~ s/(y{4}mmdd)/$date4/ig;
			$checkF =~ s/(y{2}mmdd)/$date2/ig;
		}
		# chenhuiren 090610 end
		foreach $checkF (@checkFs) # ����ÿһ��У���ļ�
		{
			my @value = &getvalue (" ",$checkF); # ��ȡ��У���ļ���Ϣ 0�ļ����� 1�ļ�·�� 2����·���б�
			$WcheckF = $SnapviewP.$commoncompP.$value[1].$value[0];
#			print "WcheckF : $WcheckF\n";
			if (( -e $WcheckF ) && ( -s $WcheckF )) # У�����У���ļ������Ҳ�Ϊ��
			{
				push (@releaseFs,$checkF) if ( $value[2] ne "" ); # У��ͨ���ҷ���·����Ϊ��������releaseFs��ʽΪ: 0�ļ����� 1�ļ�·�� 2����·���б�
				$passinfo = $passinfo."Pass ͨ�� ".$WcheckF."\n";
			}
			else
			{
				$checkfileS = 0; # ֻҪ��һ���ļ�У�鲻ͨ��,У������Ϊ0
				$nopassinfo = $nopassinfo."NotPass ��ͨ�� ".$WcheckF."\n";
			}
		}
		$allcheckfileS = 0 if ( !$checkfileS );
		&writelog($verworkP."nopassfile.log","--- $module / $owner / $ownerM ---\n".$nopassinfo."\n") if ( !$checkfileS ); # ��У��δͨ���ļ�д����ʱ�ļ�nopassfile.log
		$CFpassinfo = $nopassinfo.$passinfo;
		print "Check Files Result :\n$CFpassinfo";
	}
	else
	{
		$errorinfo = "Not Config Check Files in $WmoduleCF !\n";
	}
	&writelog($verworkP."checkfile.log","$builddatetime / $module / $owner / $ownerM\n".$CFpassinfo.$errorinfo."\n"); # �������ļ�У����д����־�ļ�
	return $checkfileS;
}
# ģ�����'У��readme'�ӹ���
sub checkreadme
{
	# input: $SnapviewP , $readmeFP , $readmeFN
	# output: $checkreadmeS , checkreadme.log
	# readmeУ����� : �ҵ������ڵ��� , �ҵ���У��ͨ�� , �һ�ȡ�����𵽵�һ����"="��ɵ���֮��������е�AllReadme.txt��
	$place = "Check_Readme";
	print "\n$module - $place......\n";
	my $errorinfo;
	if ( $readmeFN ne "" ) # ģ�������ļ���������readme
	{
		my $WreadmeF = $SnapviewP.$readmeFP.$readmeFN;
#		print "WreadmeF : $WreadmeF\n";
		if ( open(README,$WreadmeF) )
		{
			my @readme = <README>;
			close(README);
#			my $mydate = &getdate; # readmeУ����� , ���Ե�������У��
			$mydate = substr($builddate,2); # ���ݹ�˾��readme�淶 , У�����ڸ�ʽΪYYMMDD
			my @found = grep(/^\s*�汾��.+$mydate\s*$/i,@readme);
			if ( @found == 0 ) # δ�ҵ�������������
			{
				$checkreadmeS = 0;
				$RMpassinfo = "NotPass ��ͨ�� ".$WreadmeF."\n";
			}
			else  # �ҵ�������������
			{
				$RMpassinfo = "Pass ͨ�� ".$WreadmeF."\n";
				my ($line,@lines);
				my $start = 0; # ��¼��readme�ļ����ҵ�����У�������ĵ�һ��
				my $end = 0; # ��¼��readme�ļ����ҵ�����У������֮������һ��
				foreach $line (@readme)
				{
					$start = 1 if ( $line =~ /^\s*�汾��.+$mydate\s*$/i );
					$end = 1 if ( $start && ( $line =~ /^=+$/i ));
					push (@lines,$line) if ( $start && !$end );
					last if ( $end );
				}
				&writelog($verworkP."AllReadme.txt","$builddatetime / $module / $owner\n"."@lines"."\n");
			}
		}
		else
		{
			&printerror(0,"Failed Open file '$WreadmeF' !\n");
			$errorinfo = "Failed Open file $WreadmeF !\n";
		}
		&writelog($verworkP."nopassreadme.log","--- $module / $owner ---\n".$RMpassinfo."\n") if ( !$checkreadmeS ); # ��У��δͨ���ļ�д����ʱ�ļ�nopassreadme.log
#		print "checkreadmeS : $checkreadmeS\n$RMpassinfo";
		print "Check Readme Result :\n$RMpassinfo";
	}
	else
	{
		$errorinfo = "Readme File was Not Config in $WmoduleCF !\n";
	}
	# ��readmeУ����д����־�ļ�
	&writelog($verworkP."CheckReadme.log","$builddatetime / $module / $owner\n".$RMpassinfo.$errorinfo."\n"); # ��readmeУ����д����־�ļ�
	return $checkreadmeS;
}
# ģ�����'�����ļ���Ŀ¼'�ӹ���
sub releasefiles
{
	# input: $RP , $SnapviewP , $commoncompP , @releaseFs , @releaseDs
	# output: none
	$place = "Release_Files_Dirs";
	print "\n$module - $place......\n";
	# ����У���ļ�
	if ( @releaseFs != 0 ) # ��ģ�������ļ���ȡ��У���ļ�������Ϣ��У�����ļ�������Ϣ��Ϊ��ʱ
	{
		print "$module - Release_File...\n";
		my ($releaseF,$WRF,$FRP,$WFRP,@FRPs);
		foreach $releaseF (@releaseFs) # ����ÿһ�������ļ�
		{
			my @value = &getvalue(" ",$releaseF); # ��ȡ�÷����ļ���Ϣ 0�ļ����� 1�ļ�·�� 2����·���б�
			$WRF = $SnapviewP.$commoncompP.$value[1].$value[0];
			@FRPs = &getvalue(",",$value[2]); # ������·���б�תΪ����
#			@FRPs = split(/,/,$value[2]); # ������·���б�תΪ����
			foreach $FRP (@FRPs)
			{
				$FRP = &revisepath(0,$FRP); # ����·��Ϊ���ø�ʽ
				$WFRP = $RP.$FRP;
#				print "WRF : $WRF\nWFRP : $WFRP\n";
				&command("copy","f",$WRF,$WFRP); # ���ø��ǿ���Դ��������������Ϣ��error.log��
			}
		}
	}
	# �����ļ����ļ���
	if ( @releaseDs != 0 ) # ��ģ�������ļ���ȡ�ķ���Ŀ¼��Ϣ��Ϊ��ʱ
	{
		print "$module - Release_Dir...\n";
		my ($releaseD,$WRD,$DRP,$WDRP,@DRPs);
		foreach $releaseD (@releaseDs) # ����ÿһ������Ŀ¼
		{
			my @value = &getvalue(" ",$releaseD); # ��ȡ�÷���Ŀ¼��Ϣ 0Ŀ¼���� 1Ŀ¼·�� 2����·���б�
			my $RDP = &revisepath(0,$value[1]); # ����·��Ϊ���ø�ʽ
			$WRD = $SnapviewP.$commoncompP.$RDP.$value[0];
			@DRPs = &getvalue(",",$value[2]); # ������·���б�תΪ����
#			@DRPs = split(/,/,$value[2]); # ������·���б�תΪ����
			my $file; # ���ڱ�ʶ$WRD���ļ�1�����ļ���0
			if ( -e $WRD )
			{
				$file = 1;
				$file = 0 if ( -d $WRD );
				foreach $DRP (@DRPs)
				{
					$DRP = &revisepath(0,$DRP); # ����·��Ϊ���ø�ʽ
	#				print "WRD : $WRD\nWDRP : $WDRP\n";
					if ( $file )
					{
						$WDRP = $RP.$DRP;
						&command("copy","f",$WRD,$WDRP); # ���ø��ǿ���Դ��������������Ϣ��error.log��
					}
					else
					{
						$WDRP = $RP.$DRP.$value[0];
						$WDRP = $RP.$DRP if ( $OS );
						&command("copy","d",$WRD,$WDRP); # ���ø��ǿ���Դ��������������Ϣ��error.log��
					}
				}
			}
			else
			{
				&printerror(0,"Failed Found Release File or Dir '$WRD' !\n");
			}
		}
	}
	&geterror; # �д�����Ϣ��д��BuildError.log
}
# ģ�����'����readme'�ӹ���
sub releasereadme
{
	# input: $SnapviewP , $readmeFP , $readmeFN , $RP
	# output: none
	$place = "Release_Readme";
	print "\n$module - $place......\n";
	if ( $readmeFN ne "" ) # ģ�������ļ���������readme
	{
		my $WreadmeF = $SnapviewP.$readmeFP.$readmeFN;
		my $WreadmeFRP = &revisepath(1,$RP."readme"); # ����·��Ϊ���ø�ʽ
#		print "WreadmeF : $WreadmeF\nWreadmeFRP : $WreadmeFRP\n";
		if ( -e $WreadmeF )
		{
			&command("copy","f",$WreadmeF,$WreadmeFRP); # ���ø��ǿ���Դ��������������Ϣ��error.log��
			# ������λ���ϵ�readme�ļ�����Ϊ��ģ������"_"��ԭreadme�ļ���
			rename($WreadmeFRP.$readmeFN,$WreadmeFRP.$module."_".$readmeFN) || &printerror(0,"Failed Rename File '$WreadmeFRP$readmeFN' !\n");
			&geterror; # �д�����Ϣ��д��BuildError.log
		}
		else
		{
			&printerror(0,"Failed Found Readme File '$WreadmeF' !\n");
		}
	}
}
# ģ�����'����compileinfo'�ӹ���
sub releasecompileinfo
{
	# input: $SnapviewP , $compileIFP , $module , $RP
	# output: none
	$place = "Release_CompileInfo";
	print "\n$module - $place......\n";
	if ( $compileS )
	{
		my $WcompileIF = $SnapviewP.$compileIFP.$module."*"; # ��ģ��ı�����ϢΪ������Ϣ·����������ģ������ͷ���ļ�
		my $WcompileIFRP = &revisepath(1,$RP."compileinfo"); # ����·��Ϊ���ø�ʽ
#		print "WcompileIF : $WcompileIF\nWcompileIFRP : $WcompileIFRP\n";
		&command("copy","f",$WcompileIF,$WcompileIFRP); # ���ø��¿���Դ��������������Ϣ��error.log��
		&geterror; # �д�����Ϣ��д��BuildError.log
	}
}
# ģ�����'����updatelog'�ӹ���
sub releaseupdatelog
{
	# input: $updateL , $module , $RP
	# output: none
	$place = "Release_UpdateLog";
	print "\n$module - $place......\n";
	if ( $updateS )
	{
		#my $WupdateL = $verworkP."$module"."_".$builddatetime.".updt"; # ���ݱ��ű��м�����updatelog���������λ�÷���
		
		my $WupdateL = $verworkP."$module"."_".$builddatetime."_update.log"; # ���ݱ��ű��м�����updatelog���������λ�÷��� ###modify by hanjian 20120810
		my $WupdateLRP = &revisepath(1,$RP."updatelog"); # ����·��Ϊ���ø�ʽ
		
   	print "WupdateL : $WupdateL\nWupdateLRP : $WupdateLRP\n"; ####20120810 --test

		&command("copy","f",$WupdateL,$WupdateLRP); # ���ø��¿���Դ��������������Ϣ��error.log��
		&geterror; # �д�����Ϣ��д��BuildError.log
	}
}
# ģ�����'֪ͨ'�ӹ���
sub notify
{
	# input: $RP , $OS , $ucmproject , $version , $builddatetime , $modulelist , $PMM , $TEM , $SMTP , $mailfrom
	# $CFpassinfo , $RMpassinfo , @allnopassfile , @allnopassreadme , $allcheckfileS
	# output: none
	$place = "Notify";
	print "\n$module - $place......\n";
	my ($type,$rule) = @_;
	my (@text,$to,$subject,$message,$notifyRP);
	$notifyRP = $RP;
	if ( $OS ) # �����linux����ϵͳ , ����Ҫ��notifyRPת��Ϊwindows��·����ʽ
	{
		$OS = 0;
		$notifyRP = &revisepath(1,$RP);
		$OS = 1;
	}
#	@allnopassreadme = ("��δͨ��readme\n") if ( @allnopassreadme == 0 );
	$text[0] = "AutoBuild_Notify: ";
	&getallmodules;
	$text[1] = " , ����!\n\n���α�����Ϣ����: \n��Ŀ����: $UCMprj\n��Ŀ�汾��: $version\n�Զ��ű����п�ʼʱ��: $builddatetime\n���뱾���Զ��ű����е�ģ���б�: \n"."@allmodules"."\n�汾����λ��: $notifyRP\n\n";
	undef(@allmodules);
	$text[2] = "���뻷��������ʱ���: ���ڷ���λ���µ�BuildInfo�ļ��в���;\nԴ����¼�¼: ���ڷ���λ���µ�updatelog�ļ����в���;\n������Ϣ: ���ڷ���λ���µ�compileinfo�ļ����в���.\n(ReadMe: ���ڷ���λ���µ�readme�ļ����в���.)\n\n�����������µ��������ù���Ա�����ʼ���$CMOM��\n���ʼ���ϵͳ��������ظ���лл��\n\n";
	if ( !$checkfileS )
	{
		open (NOPASSF,$verworkP."nopassfile.log"); # ��ȡУ��δͨ���ļ�����ʱ�ļ�nopassfile.log
		my @allnopassfile = <NOPASSF>;
		close (NOPASSF);
		@allnopassfile = ("��δͨ���ļ�\n") if ( @allnopassfile == 0 );
		if ( $type )
		{
			$text[3] = "�ļ�У��δͨ��";
			$text[4] = "У���ļ����: \n$CFpassinfo\n";
			$text[5] = "��: ��ģ��֮ǰ����У��δͨ�����ļ�\n@allnopassfile\n";
		}
		else
		{
			$text[5] = "��1.1: ����У��δͨ�����ļ�\n@allnopassfile\n";
		}
	}
	#########add by hanjian 20120417(mail update.log)################hanjian 20120810 
	open (UPDATE333,$verworkP."all-code_".$OSstring."_".$builddatetime."_update.log.mail"); # ��ȡ�ļ�update.log
		my @updatelogss = <UPDATE333>;
		close (UPDATE333);
##		@updatelogss = ("��ģ��Դ����¼�¼: ���ڷ���λ���µ�updatelog�ļ����в���\n") if ( @updatelogss == 0 );
##	  $text[6]= $text[6]."�����updatelog:\n @updatelogss\n";
	  if ( @updatelogss == 0 )
	  {
	  	$text[6]= "��ģ��Դ����¼�¼: ���ڷ���λ���µ�updatelog�ļ����в���\n";
	  }
	  else
	  {
	  	$text[6]= $text[6]."�����updatelog:\n @updatelogss\n";
	  } 
	  
	################################################################
	if ( $CRM && !$checkreadmeS )
	{
		open (NOPASSRM,$verworkP."nopassreadme.log"); # ��ȡУ��δͨ��readme����ʱ�ļ�nopassreadme.log
		my @allnopassreadme = <NOPASSRM>;
		close (NOPASSRM);
		@allnopassreadme = ("��δͨ��readme\n") if ( @allnopassreadme == 0 );
		if ( $type )
		{
			$text[3] = $text[3]."ReadmeУ��δͨ��";
			$text[4] = $text[4]."У��Readme���: \n$RMpassinfo\n";
		}
		else
		{
			$text[5] = $text[5]."��1.2: ����У��δͨ����readme\n@allnopassreadme\n";
		}
	}
	if ($type) # ����ģ���֪ͨ���� , �ù�����spbuild��intbuild�й� , ��У�鲻ͨ��ʱ��֪ͨ
	{
		$subject = $text[0]."$owner ģ��$module����$UCMprj $version $builddatetime�ı��� , $text[3]!";
		$text[4] = "�������ģ��: $module\n".$text[4];
		$message = $owner.$text[1].$text[4].$text[2].$text[5];
		$to = $ownerM;
		$to = $to.",".$gownerM if ( $rule ); # �����intbuild , �򵥸�ģ��֪ͨС�鸺����
		&writelog($verworkP."sendmail.log","$builddatetime / $module / $to\n");
		&mail($to,$subject,$message);
	}
	else # ����ģ���֪ͨ���� , �ù�����spbuild��intbuild�й� , ���뱨��
	{
		$subject = $text[0]."$UCMprj $version $builddatetime�ı��뱨��";
		$message = "$UCMprj $version �汾��ظ�����".$text[1].$text[2].$text[5].$text[6];
		if ($rule) # intbuild֪ͨ����
		{
			$to = $CMOM.",".$PMM.",".$TEM;
		}
		else # spbuild֪ͨ����
		{
			$to = $CMOM;
			$to = $to.",".$TEM if ( $allcheckfileS );
		}
		&writelog($verworkP."sendmail.log","$builddatetime / $to\n");
		&mail($to,$subject,$message);
	}
}
# ���ڷ����ʼ�
sub mail
{
	# input: recipients , attachment
	# output: email status log
	my ($to,$subject,$message) = @_;
	$place = "Send_Mail";
	print "$place...\n";
	use Mail::Sendmail;
	%mail = ( To      => $to,
	          From    => $mailfrom,
	          Subject => $subject,
	          Message => $message
	         );
	$mail{Smtp} = $SMTP;
	$mail{'Content-Type'} = 'text/plain; charset="GB2312"';
	if ( sendmail(%mail) )
	{
		&writelog($verworkP."sendmail.log","Successfully Send Mail !\n".$Mail::Sendmail::log."\n"); # �������ļ�У����д����־�ļ�
		print "Successfully Send Mail !\n";
	}
	else
	{
		&writelog($verworkP."sendmail.log","Failed Send Mail !\n".$Mail::Sendmail::error."\n"); # �������ļ�У����д����־�ļ�
		&printerror(0,"Failed Send Mail !\n");
	}
	no Mail::Sendmail;
}
# ��ȡ����ű����е�����ģ�� , ����д��BUILDINFO,AllReadme(���������)���ʼ�����
sub getallmodules
{
	# input: allmodules.log
	# output: @allmodules
	if ( open (ALL,$verworkP."allmodules.log") ) # ��ȡУ��δͨ��readme����ʱ�ļ�nopassreadme.log
	{
		@allmodules = <ALL>;
		close(ALL);
	}
	else
	{
		&printerror(0,"Failed Open File '$verworkP"."allmodules.log' !\n");
	}
}
# =====================================================================================================================
# ϵͳ������ú���
sub command
{
	# input: commandkey (update , exe , copy , del , updatecopy , pause), command items
	# output: $vererrL
	my ($cmd,$item0,$item1,$item2) = @_;
	if ( $cmd eq "env" )
	{
		print "Command : $cmd : $item0\n";
		my $output = "";
		if ( $OS )
		{
			$output = `env | grep $item0`;
		}
		else
		{
			$output = `set $item0`;
		}
		return $output;
		undef($output);
	}
	elsif ( $cmd eq "del" )
	{
		print "Command : $cmd : $item1\n";
		if ( $OS )
		{
			if ($item0 eq "d"){
				system("rm -fr $item1 2>$vererrL"); # �ݹ�ɾ��Ŀ¼�������ļ���Ŀ¼
			}else{ # if ($item0 eq "f")
				system("rm -f $item1 2>$vererrL");
			}
		}
		else
		{
			if ($item0 eq "d"){
				system("del /s /f /q $item1 2>>$vererrL"); # �ݹ�ɾ��Ŀ¼�������ļ�
			}else{ # if ($item0 eq "f")
				system("del /f /q $item1 2>>$vererrL");
			}
		}
	}
	elsif ( $cmd eq "update" )
	{
		print "Command : $cmd : $item0 $item1\n";
		######system("cleartool update -f -ove -log $item1 $item0 2>$vererrL");
		if ( $OS )
		{
			#system("export LANG="en_US.UTF-8"");
				#$ENV{LANG} = "en_US.UTF-8";
				
#        system("svn update  --accept theirs-full --username root --password 'kdckdc' --no-auth-cache $item0  >$item1");####--- modify by hanjian 20120821  for update 
#		    system("svn update  --accept theirs-full --username root --password 'kdckdc' --no-auth-cache $item0 2>$vererrL");
		}
		else
		{
			  system("svn update  --accept theirs-full $item0  >$item1");####--- modify by hanjian 20120821  for update 
		    system("svn update  --accept theirs-full $item0 2>$vererrL");
		}
	  
	  
		
		#system("svn update --username root --password 'kdckdc' --no-auth-cache $item0  >$item1");####--- modify by hanjian 20120803  for update 
		#system("svn update --username root --password 'kdckdc' --no-auth-cache $item0 2>$vererrL"); ####---- add by hanjian 20120803  for update 
	}
	
	elsif ( $cmd eq "update_i" )#####add  if git or svn 20140612
	{
		print "Command : $cmd : $item0 $item1\n";
		system("echo ============================================================== >>$item1");
		system("echo -------------------------------------------------------------- >>$item1.mail");
		######system("cleartool update -f -ove -log $item1 $item0 2>$vererrL");
		if ( $OS )
		{    
			#system("export LANG="en_US.UTF-8"");
			     &getenv(@envs);
				$ENV{LANG} = "en_US.UTF-8";
				if (-e ".svn"){
					#system("svn cleanup   --username root --password 'kdckdc' --no-auth-cache $item0  ");
					#system("svn revert   --username root --password 'kdckdc' --no-auth-cache $item0  ");
#					system("svn info --username root --password 'kdckdc' --no-auth-cache $item0|grep URL  >>$item1 ");
#					system("svn info --username root --password 'kdckdc' --no-auth-cache $item0|grep URL  >>$item1.mail "); ###use  $item1.mail  as mail context --add 20140707
					system("echo now updating.... >>$item1");
#        	system("svn update  --accept theirs-full --username root --password 'kdckdc' --no-auth-cache $item0  >>$item1");####--- modify by hanjian 20120821  for update 
#		    	system("svn update  --accept theirs-full --username root --password 'kdckdc' --no-auth-cache $item0 2>$vererrL");
		    	system("echo the lasted log.... >>$item1");
#		    	system("svn log -l 1 --username root --password 'kdckdc' --no-auth-cache $item0  >>$item1 ");
		    	my $file_mail="tmp.log";
		    	system("svn log -l 1 -q --username root --password 'kdckdc' --no-auth-cache $item0 >$file_mail");
		    	open (FILE,$file_mail)or die "can't open tmp.log:$!";  #����־
          my @log_mail=<FILE>;
          close (FILE); 
		    	my @num_mail=split(/\|/,$log_mail[1]);
		    	system("echo $num_mail[0]  >>$item1.mail");
		    	
		    }elsif(-e ".git"){
		    	system("git remote -v |grep '(fetch)' >>$item1");
		    	system("git remote -v |grep '(fetch)' >>$item1.mail");
		    	system("git branch |grep '*' >>$item1");
		    	system("git branch |grep '*' >>$item1.mail");
		    	system("echo now pull ing.... >>$item1");
		    	system("git pull >>$item1 ");
		    	system("git pull 2>$vererrL");
		    	system("echo the lasted log.... >>$item1");
		    	system("git log -n 1 >>$item1");
		    	system("git log -n 1 --pretty=format:commit:%H%n >>$item1.mail");
		    	
		    }else{
		    	print "it is  not a workspace\n";
		    }
		}
		else
		{
				if (-e ".svn"){
					#system("svn cleanup   $item0  ");
					#system("svn revert   $item0  ");
					system("svn info     $item0|grep URL   >>$item1 ");
					system("svn info     $item0|grep URL   >>$item1.mail ");
					system("echo now updating.... >>$item1");
        	system("svn update   $item0  >>$item1"); 
		    	system("svn update   $item0 2>$vererrL");
		    	system("echo the lasted log.... >>$item1");
		    	system("svn log -l 1 .>>$item1");
		    	my $file_mail="tmp.log";
		    	system("svn log -l 1 -q $item0 >$file_mail");
		    	open (FILE,$file_mail)or die "can't open tmp.log:$!";  #����־
          my @log_mail=<FILE>;
          close (FILE); 
		    	my @num_mail=split(/\|/,$log_mail[1]);
		    	system("echo $num_mail[0] >>$item1.mail");
		    	
		    	
		    }elsif(-e ".git"){
		    	system("git remote -v |grep '(fetch)' >>$item1");
		    	system("git remote -v |grep '(fetch)' >>$item1.mail");
		    	system("git branch |grep '*' >>$item1");
		    	system("git branch |grep '*' >>$item1.mail");
		    	system("echo now pull ing.... >>$item1");
		    	system("git pull >>$item1 ");
		    	system("git pull 2>$vererrL");
		    	system("echo the lasted log.... >>$item1");
		    	system("git log -n 1 >>$item1");
		    	system("echo \n >>$item1.mail");
		    	system("git log -n 1 --pretty=format:commit:%H%n >>$item1.mail");
		    	
		    }else{
		    	print "it is  not a workspace\n";
		    }
			 	  
		}
		#system("svn update --username root --password 'kdckdc' --no-auth-cache $item0  >$item1");####--- modify by hanjian 20120803  for update 
		#system("svn update --username root --password 'kdckdc' --no-auth-cache $item0 2>$vererrL"); ####---- add by hanjian 20120803  for update 
	}
	
	elsif ( $cmd eq "disreadonly" )
	{
		print "Command : $cmd : $item0\n";
		$item0 = $item0."*"; # ��disreadonly����·�������⴦��
		if ( $OS )
		{
			system("chmod -R 755 $item0 2>$vererrL"); # �ݹ�ȥ��Ŀ¼$item0�������ļ���ֻ������
		}
		else
		{
			system("attrib -R $item0 /s 2>>$vererrL"); # �ݹ�ȥ��Ŀ¼$item0�������ļ���ֻ������
		}
	}
	elsif ( $cmd eq "exe" )
	{
		my $mycurrpath = &getcurrpath;
		print "Command : $cmd : $mycurrpath$item0 $item1\n";
		if ( $OS )
		{
			system("./$item0 >$item1 2>&1"); # ִ���ļ�$item0 , д����־$item1
			system("cat $item1");
		}
		else
		{
			system("$item0 >$item1 2>>&1"); # ִ���ļ�$item0 , д����־$item1
			system("type $item1");
		}
		undef($mycurrpath);
	}
	elsif ( $cmd eq "copy" )
	{
		print "Command : $cmd : $item1 $item2\n";
		if ( $OS )
		{
			system("mkdir -p $item2 2>$vererrL") if ( !-e $item2 ); # ����linux��������cp��֧���Զ�������Ŀ¼ , ������ж�Ŀ��Ŀ¼�Ƿ���� , Ŀ��Ŀ¼�����ڣ����ȴ�����Ŀ¼
			if ($item0 eq "d"){
				system("cp -fr --preserve=timestamps --dereference $item1 $item2 2>$vererrL");
			}else{ # if ($item0 eq "f")
				system("cp -f --preserve=timestamps --dereference $item1 $item2 2>$vererrL");
			}
		}
		else
		{
			if ($item0 eq "d"){
				system("xcopy $item1 $item2 /e /i /y /c /k /v 2>>$vererrL");
			}else{ # if ($item0 eq "f")
				system("xcopy $item1 $item2 /i /y /c /k /v 2>>$vererrL");
			}
		}
	}
	elsif ( $cmd eq "mkbl" )
	{
		my $mycurrpath = &getcurrpath;
		print "Command : $cmd : $mycurrpath\n";
		my $output = `cleartool mkbl -all 2>$vererrL`;
		return $output;
		undef($output);
	}
	elsif ( $cmd eq "chstream" )
	{
		my $mycurrpath = &getcurrpath;
		print "Command : $cmd : $mycurrpath\n";
		system("cleartool chstream -recommended -default -cview 2>$vererrL");
	}
	elsif ( $cmd eq "lsstream" )
	{
		my $mycurrpath = &getcurrpath;
		print "Command : $cmd : $mycurrpath\n";
		my $output = `cleartool lsstream -fmt \"\%\[rec\_bls\]p\\n\" -cview 2>$vererrL`;
		return $output;
		undef($output);
	}
	elsif ( $cmd eq "setcs" )
	{
		my $mycurrpath = &getcurrpath;
		print "Command : $cmd : $mycurrpath\n";
		system("cleartool setcs -stream 2>$vererrL");
	}
	elsif ( $cmd eq "mkdir" )
	{
		print "Command : $cmd : $item0\n";
		if ( $OS )
		{
			system("mkdir --mode=750 -p $item0 2>$vererrL");
		}
		else
		{
			system("mkdir $item0 2>$vererrL");
		}
	}
	elsif ( $cmd eq "pause" ) # ��ͣ����
	{
		print "Press Enter to continue ...\n";
		<STDIN>;
#		`pause`;
	}
}
# ���ڽ�ָ�����ִ���ӡ����Ļ , ������Ϣ��־BUILDERROR�ͱ�����ϢBUILDINFO , ���ṩ��ȷ������Ϣ
sub printerror
{
	# input: print type , print string , show key
	# output: none
	my ($type,$string,$key) = @_;
	$string = "Error : $module : $place : ".$string if ( $type == 0 );
	print BUILDERROR $string; # ����ӡ���ִ�д����־ , ��¼ģ���λ��
	print $string;  # ��ӡ�ִ�����Ļ
#	if ( $key ne "" )
#	{
#		my @found = &getline(0,"#$key",@demofile);
#		if ( @found != 0 )
#		{
#			print join("\n",@found); # �������ļ���ȡ�Դ�ӡ�ؼ��ֿ�ͷ����ȷ������Ϣ
#			print "\n";
#		}
#		@found = &getline(0,"# ",@demofile);
#		if ( @found != 0 )
#		{
#			print join("\n",@found); # �������ļ���ȡͨ�õ���ȷ������Ϣ
#			print "\n";
#		}
#		@found = &getline(0,"$key",@demofile);
#		if ( @found != 0 )
#		{
#			print "Example:\n";
#			print join("\n",@found); # �������ļ���ȡͨ�õ���ȷ������Ϣ
#			print "\n";
#		}
#	}
}
# ��ָ�����ִ�д��ָ������־
sub writelog
{
	# input: log file path , string to write
	# output: none
	my ($logfile,$string) = @_;
	if ( open (LOG,">>$logfile") )
	{
		print LOG $string;
		close(LOG);
	}
	else
	{
		&printerror(0,"Failed Open File '$logfile' !\n");
	}
}
# ��error�ļ�,���������ж��Ƿ񱨴�,֮���ӡ��־�����error�ļ�
sub geterror
{
	# input: type(err: return 1 when found "error"; warn: return 1 when found "error" or "warnning"; except: return 0 when found except $string in first line )
	# output: found resule
	my ($type,$string) = @_;
	my $result = 0;
	if ( -s $vererrL )
	{
		if ( open (ERROR,$vererrL) )
		{
			my @errors = <ERROR>;
			close (ERROR);
			if ( @errors > 0 )
			{
				if ( $type =~ /err/i )
				{
					my @found = grep(/error/i,@errors);
					$result = 1 if ( @found > 0 );
				}
				elsif ( $type =~ /warn/i )
				{
					my @found = grep(/warn|error/i,@errors);
					$result = 1 if ( @found > 0 );
				}
				elsif ( $type =~ /except/i )
				{
					$result = 1;
					$result = 0 if (( @errors == 1 ) && ( $string ne "" ) && ( $errors[0] =~ /$string/i ));
				}
				else
				{
					$result = 1;
				}
				&printerror(0,"@errors");
			}
			else
			{
				$result = 0;
			}
		}
		else
		{
			&printerror(0,"Failed Open Error File '$vererrL' !\n");
			$result = 1;
		}
	}
	# ���error�ļ�����
	if ( open (ERROR,">$vererrL") )
	{
		close (ERROR);
	}
	else
	{
		&printerror(0,"Failed Clean Error File '$vererrL' !\n");
	}
	return $result;
}

# ����ļ�����
sub cleanerror
{
	# input: none
	# output: none
	undef (@errors);
	if ( open (ERROR,">$vererrL") )
	{
		close (ERROR);
		return 1;
	}
	else
	{
		&printerror(0,"Failed Clean Error File '$vererrL' !\n");
		return 0;
	}
}

# =====================================================================================================================
# ����·��Ϊ���ø�ʽ , ���ø�ʽΪ: ����·������$OS����'\'��'/'��β ; �����������Ͳ�ͬȥ����ͷ��'\'��'/' , ����'\\''/'��ͷ
sub revisepath
{
	# input: ��������(1-������ͷ , 0-��������ͷ) , ����·��
	# output: ���ø�ʽ��·�� : 1-linux����Ϊ'/'��ͷ'/'��β , windows����Ϊ'\\'��ͷ'\'��β ; 0-linux����Ϊ'/'��β , windows����Ϊ'\'��β , ������ͷ
	my ($type,$path) = @_;
	if ( $path =~ /^[\\\/]+$/ )
	{
		return "";
	}
	else
	{
		my (@part,$path1);
		@part = split(/[\\\/]+/,$path); # �Զ������'\'��'/'������з���������·�� , �����ϵͳ�޹�
		shift(@part) if ( $part[0] eq "" ); # ���'\'��'/'��ͷ , ��ȥ���з�����ĵ�һ��Ԫ�� , ����������Ԫ��ʱ����ȥ����ͷ��'\'��'/'
#		shift(@part) if ( $path =~ /^[\\\/]+/ );
		if ( $OS ) # linux
		{
			$path1 = join("/",@part); # ��'/'����
			$path1 = $path1."/" if ( $type != 2 ); # ��������ʱ�Զ�ȥ����β����'/' , ��˼���
			$path1 = "/".$path1 if (( $type == 1 ) && ( $path =~ /^[\\\/]+/ )); # ����'/'��ͷͬʱ��������Ϊ������ͷ , ����'/'��ͷ
		}
		else # windows
		{
			$path1 = join("\\",@part); # linux��'\'����
			$path1 = $path1."\\" if ( $type != 2 ); # ��������ʱ�Զ�ȥ����β����'\' , ��˼���
			$path1 = "\\\\".$path1 if (( $type == 1 ) && ( $path =~ /^[\\\/]+/ )); # ����'/'��ͷͬʱ��������Ϊ������ͷ , ����'\\'��ͷ��������·��
		}
		return $path1;
	}
}
# ��ָ�������в�����ָ���ؼ��ִ�ͷ��Ԫ��(�ڶ����в���������������)
sub getline
{
	# input: ��������(0-�Թؼ��ֿ�ͷ 1-�Թؼ��ּ�"="��ͷ) , lines to be find , ���ҹؼ���
	# output: lines that beginwith the keyword, ignore blank/tab
	my ($type,$keyword,@lines) = @_;
	my ($l,@line);
	if ( $type == 0 )
	{
		@line = grep(/^\s*$keyword\s+/i,@lines); # �Թؼ���+�ո��tab��ͷ
	}
	elsif ( $type == 1 )
	{
		@line = grep(/^\s*$keyword\s*=/i,@lines); # �����Թؼ��ֺ�"="ǰ��Ŀո��tab
	}
	elsif ( $type == 2 )
	{
		@line = grep(/^\s*$keyword.*\s+/i,@lines); # �Թؼ���+�����ַ�+�ո��tab��ͷ
	}
	foreach $l (@line)
	{
		$l =~ s/^\s+|\s+$//g ; # ȥ�����ҵ��е���β�ո��tab
	}
	return (@line);
}
# ��ָ���ؼ����з��ַ���
sub getvalue
{
	# input: �ָ�� , �����ַ���
	# output: �ָ�õĸ�����
	my ($separator,$line) = @_;
	my @value;
	if ( $separator eq " " )
	{
		@value = split(/\s+/,$line); # ���Էָ��ǰ��Ŀո��tab
	}
	else
	{
		@value = split(/\s*$separator+\s*/i,$line); # ���Էָ��ǰ��Ŀո��tab
	}
	shift(@value) if ( $value[0] eq "" );
	return (@value);
}
#
sub shiftvalue
{
	# input: $key , 
	# output: �ָ�õĸ�����
	my ($value,@array) = @_;
	my $a;
	foreach $a (@array)
	{
		$a =~ s/^$value\s+//ig; # ȥ����ͷ��ֵ�Ϳո�
	}
	return @array;
}
# ��ȡ��ǰ����·��
sub getcurrpath
{
	# get current work dir
	my $curpath;
	if ( $OS )
	{
		$curpath = `pwd`;
	}
	else
	{
		$curpath = `cd`;
	}
	chomp($curpath);
	$curpath = &revisepath(1,$curpath);
	return $curpath;
}
# ��ȡ��������yymmdd
sub getdate
{
    my ($sec, $min, $hour, $mday, $mon, $year, $wday, $yday, $time) = localtime();
    return sprintf("%4d%2.2d%2.2d", $year + 1900, $mon + 1, $mday);
}
# ��ȡ��������-ʱ��yymmdd-hhmm
sub getdatetime
{
	# input: none
	# output: current date-time
    my ($sec, $min, $hour, $mday, $mon, $year, $wday, $yday, $time) = localtime();
    return sprintf("%4d%2.2d%2.2d-%2.2d%2.2d", $year + 1900, $mon + 1, $mday,  $hour, $min);
}

# ��ȡ����ʱ��hhmm
sub get_nowtime
{
	# input: none
	# output: current date-time
    my ($sec, $min, $hour, $mday, $mon, $year, $wday, $yday, $time) = localtime();
    return sprintf("%2.2d%2.2d", $hour, $min);
}

#----------20150915 add ----

#��ȡwindows�汾�� ���ڴ�������Ŀ¼  20150915 add
sub get_win_version
{
  
  my $svnRev;#svn�汾��
  
  chdir $SnapviewP;
  my @svnRev_l=`svn info .`;
  print $svnRev_l[9];
  my @svnR=split ":",$svnRev_l[9];
  print "====\n";
  print $svnR[1];
    print "====\n";
  $svnRev=$svnR[1];
  $svnRev =~ s/\s//g;  
  return $svnRev;
}

sub get_linux_version
{

  my $svnRev;#svn�汾��
  	
  chdir $SnapviewP;
  print "��ǰĿ¼Ϊ��$SnapviewP\n";
 
  my @svnRev_l=`/usr/local/svn/bin/svn info . --username root --password kdckdc`;

  
  #$svnRev_l[5]=Encode::decode("gb2312","$svnRev_l[5]");
  print $svnRev_l[9];
  my @svnR=split ":",$svnRev_l[9];
  print "====\n";
  print $svnR[1];
    print "====\n";
  $svnRev=$svnR[1];
  $svnRev =~ s/\s//g;
	
	return $svnRev;
}

# =====================================================================================================================
return 1;
# End
