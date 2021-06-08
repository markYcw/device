#!usr/bin/perl
# ======================================================================
#
# Perl Source File -- Created with SAPIEN Technologies PrimalScript 3.1
#
# NAME: module build process
#
# AUTHOR: pengjing
# DATE  : 2007-7-26 ~ 2007-9-4
#
# PURPOSE: 
#
# ======================================================================
#
# parameter usage -----
# 0 options : not necessary
# 0.0 defause none : whole build process , check file , notify bases spbuild rule
# 0.1 -u : update src code
# 0.2 -b : build(compile)
# 0.30 -c : check file and readme
# 0.31 -cf : check file
# 0.4 -r : release
# 0.50 -n : notify bases on spbuild rule , must be used with -c that means check and notify
# 0.51 -ni : notify bases on intbuild rule , must be used with -c that means check and notify
# 1 version : the version of the project , necessary
# 2 modulelist : module of the version , necessary
# 3 specRP : specifical release path , not necessary
#
# log file define -----
# checkfile.log
# checkreadme.log
# notify.log
# BuildError.log
# BuildInfo.txt
# =====================================================================================================================
print "\n=======================================================================\n";
print "\n                             SP BUILD START                            \n";
print "\n=======================================================================\n";
# У��ű��������
$place = "Validate_Script_Para";
print "\n$module - $place......\n";
# ��"-"��ͷ����Ϊ������options,�����������3��4��
for ( my $i = 0 ; $i < @ARGV; $i++ )
{
	if ( $ARGV[$i] =~ /^-/ ) # "-"��ʶoptions
	{
		if ( $spoptions eq "" )
		{
			$spoptions = $ARGV[$i];
			$spoptions =~ s/^-//g; # ȥ��������ʶ��'-'֮����д���
			if ( $spoptions eq "" )
			{
				print "Invalid Option '$ARGV[$i]' !\n";
				exit 1; # �����Ĺؼ�����ȻΪ�����˳���������
			}
			$spoptions = lc($spoptions); # ������ת��ΪСд
			my @part = split("",$spoptions); # �ָ�����ı�����̹ؼ��� , ���У��
			my @preoptions = qw(u b c f r n);
			for( my $j = 0; $j < @part ; $j++ ) # �ж�����Ĺؼ����Ƿ���ȷ
			{
				my @found = grep(/$part[$j]/,@preoptions);
				if ( @found == 0 )
				{
					print "Unknow Option '$part[$j]' in '$ARGV[$i]' !\n";
					exit 1; # �����Ĺؼ�����ȻΪ�����˳���������
				}
			}
			undef(@preoptions);
			undef(@part);
			undef(@found);
			undef($j);
			if ((( $spoptions =~ /n/ ) && (( $spoptions !~ /c/ ) || ( $spoptions !~ /r/ ))) || ( $spoptions =~ /f/ ) && ( $spoptions !~ /c/ ))
			{
				print "Unable 'Notify' without 'Check' and 'Release', or\nUnable Identify 'i' without 'n' , or Unable Identify 'f' without 'c' !\n";
				exit 1; # �����Ĺؼ���֮�䲻ƥ�����˳���������(ƥ�����Ϊ:n������cͬʱʹ�� , i������nͬʱʹ�� , f������cͬʱʹ��)
			}
		}
		else
		{
			print "ReDefine Options '$ARGV[$i]' after '$spoptions' !\n";
			exit 1; # �ض������ , ���˳�
		}
	}
	elsif ( $ARGV[$i] =~ /^\+/ ) # "-"��ʶoptions
	{
		if ( $spenvs eq "" )
		{
			$spenvs = $ARGV[$i];
			$spenvs =~ s/^\+//g; # ȥ��������ʶ��'-'֮����д���
			if ( $spenvs eq "" )
			{
				print "Invalid Env '$ARGV[$i]' !\n";
				exit 1; # �����Ĺؼ�����ȻΪ�����˳���������
			}
			if ( !open(ENV,$workpath."env.ini") )
			{
				print "Failed Open File '$workpath"."env.ini' !\n";
				exit 1; # �Ҳ����������������ļ����˳���������
			}
			@envfile = <ENV>;
			close(ENV);
			my @part = split(/,+/,$spenvs); # �ָ�����ı�����̹ؼ��� , ���У��
			shift(@part) if ( $part[0] eq "" );
			for( my $j = 0; $j < @part ; $j++ ) # �ж�����Ĺؼ����Ƿ���ȷ
			{
				my @found = grep(/^$part[$j]\s+/i,@envfile);
				if ( @found == 0 )
				{
					print "Unknow Env '$part[$j]' in '$ARGV[$i]' !\n";
					exit 1; # �����Ĺؼ�����ȻΪ�����˳���������
				}
			}
			undef(@envfile);
			undef(@part);
			undef(@found);
			undef($j);
		}
		else
		{
			print "ReDefine Env '$ARGV[$i]' after '$spenvs' !\n";
			exit 1; # �ض������ , ���˳�
		}
	}
	elsif ( $version eq "" ) # ��һ������Ϊ�汾��
	{
		$version = $ARGV[$i];
	}
	elsif ( $modulelist eq "" ) # �ڶ�������Ϊģ���б�
	{
		$modulelist = $ARGV[$i];
	}
	elsif (( $spspecRP eq "" ) && ( $ARGV[$i] =~ /^[\\\/]+/ )) # ����������Ϊָ������·��
	{
		$spspecRP = $ARGV[$i];
	}
	else
	{
		print "Redundant ARGVmeters '$ARGV[$i]' !\n";
		exit 1; # ������������ȷ���˳���������
	}
}
undef($i);
if ( $version eq "" )
{
	print "Lost 'Version' ARGVmeters in '@ARGV' !\n";
	exit 1; # ȱ�ٹؼ�����
}
if ( $modulelist eq "" )
{
	print "Lost 'ModuleList' ARGVmeters in '@ARGV' !\n";
	exit 1; # ȱ�ٹؼ�����
}
# =====================================================================================================================
# �ؼ�����������"AUTOBUILD_PATH"��ֵ�����ű�����·��,�����ù���Ա�ֶ�����
$place = "Get_AutoBuild_ENV";
print "\n$module - $place......\n";
$autobuildpath = $ENV{'AUTOBUILD_PATH'};
if ( -e $autobuildpath && chdir($autobuildpath) )
{
	push (@INC,$autobuildpath);
	$workpath = $autobuildpath;
}
else
{
	print "Warnning : OS ENV 'AUTOBUILD_PATH' Not Exit or Incorrectly Config !\n\n";
}
require ($workpath."common.pl");
if ( $spoptions eq "" )
{
	if ( $OS && $ISM )
	{
		&printerror(1,"Linux SourceCode is mounted , Skip Update View !\n");
		$spoptions = "bcrn";
	}
	else
	{
		$spoptions = "ubcrn";
	}
}
# =====================================================================================================================
# ������� , �����ñ��뻷������
if ( $spoptions =~ /b/ )
{
	$place = "Get_Build_Env";
	print "\n$module - $place......\n";
	if ( $spenvs eq "" )
	{
		$spenvs = $defenv;
	}
	else
	{
		$spenvs = $spenvs.",".$defenv if ( $spenvs =~ /^,*GROUPS[\d\._]*,*$/i );
	}
}
else
{
	$spenvs = "-" if ( $spoptions !~ /u/ );
}
&getversionprocess;
sub getversionprocess
{
  if ( $OS )
  {
    #$version_name=&get_version;
    $version_name=&get_linux_version;
    chomp($version_name);
    print "!!!!!*****$version_name!!!!!!\n";
    open (OUTFILE,">./outfile");    
	print OUTFILE  $UCMprj."_".$version."_R".$version_name."#T".$buildtime;
    close(OUTFILE);
  }
  else
  {
    $version_name=&get_win_version;
    chomp($version_name);
    print "!!!!!*****$version_name!!!!!!\n";
    open (OUTFILE,">./outfile");
    print OUTFILE  $UCMprj."_".$version."_R".$version_name."#T".$buildtime;
    close(OUTFILE);
  }
}

# ������� , ������·��
if ( $spoptions =~ /r/ )
{
	# ��ȡ���յķ���·��
	if ( $spspecRP eq "" ) # ���û�û��ָ������·�� , ���ݷ��������ڷ���·�������ǰ�ļ���/��ǰʱ���ļ���������ÿ�η���
	{
		my $p = &revisepath(0,$builddate."-SP/".$UCMprj."_".$version."_R".$version_name."#T".$buildtime);
		#my $p = &revisepath(0,"$builddate-SP/$builddatetime");
		$RP = $ReleaseP.$p;
	}
	else
	{
		$RP = &revisepath(1,$spspecRP);
	}
	&printerror(1,"Build_Single : ReleasePlace : $RP\n");
}
undef($spspecRP);
# =====================================================================================================================
@spmodules = &getvalue(",",$modulelist);
# �ڵ���Ԥ����֮ǰ , д��allmodules.log , ����д��BUILDINFO,AllReadme(���������)���ʼ�����
&writelog($verworkP."allmodules.log","@spmodules");
undef($modulelist);
# ������̿�ʼ֮ǰ��Ԥ����
&preprocess($spoptions);
# ������̿�ʼ
$place = "Module_Process";
print "\n$module - $place......\n";
&module($spoptions,$spenvs,@spmodules);
undef($spenvs);
undef(@spmodules);
# �������֮���������
&afterprocess($spoptions);
undef($spoptions);
# =====================================================================================================================
# �رձ��������Ϣ�ļ�
close(BUILDERROR);
print "\n================================= END =================================\n";
#&command("pause"); # chenhuiren 090610 
# End