#!usr/bin/perl
#
# Perl Source File -- Created with SAPIEN Technologies PrimalScript 3.1
#
# NAME: module build process
#
# AUTHOR: chenhuiren , xuping ,pengjing
# DATE  : 
#
# PURPOSE: 
#
# =====================================================================================================================

#add   by  fangyanzhi  :   begin
use  File::Copy;
use  Cwd;
use   Encode;
my  $CUR_OS=$^O;
#use  File::Find;
$TOP_DIR=getcwd;
chomp($TOP_DIR);

			if ( -e  nopass.html )
				{
				unlink nopass.html;
				&command("del","f","nopass.html");
				}

# add   by  fangyanzhi  : end

print "\n=======================================================================\n";
print "\n                            INT BUILD START                            \n";
print "\n=======================================================================\n";
# У��ű��������
$place = "Validate_Script_Para";
print "\n$module - $place......\n";
# def 3
for ( my $i = 0 ; $i < @ARGV; $i++ )
{
	if ( $ARGV[$i] =~ /^-/ ) # "-"��ʶoptions
	{
		if ( $intoptions eq "" )
		{
			$intoptions = $ARGV[$i];
			$intoptions =~ s/^-//g; # ȥ��������ʶ��'-'֮����д���
			if ( $intoptions eq "" )
			{
				print "Invalid Option '$ARGV[$i]' !\n";
				exit 1; # �����Ĺؼ�����ȻΪ�����˳���������
			}
			$intoptions = lc($intoptions); # ������ת��ΪСд
			if (( $intoptions ne "a" ) && ( $intoptions ne "s" ))
			{
				print "Invalid Option '$ARGV[$i]' !\n";
				exit 1; # �����Ĺؼ�����ȻΪ�����˳���������
			}
		}
		else
		{
			print "ReDefine Options '$ARGV[$i]' after '$intoptions' !\n";
			exit 1; # ������������ȷ���˳���������
		}
	}
	elsif ( $version eq "" ) # ��һ������Ϊ�汾��
	{
		$version = $ARGV[$i];
	}
	elsif (( $intspecRP eq "" ) && ( $ARGV[$i] =~ /^[\\\/]+/ )) # ����������Ϊָ������·��
	{
		$intspecRP = $ARGV[$i];
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
if (( $intoptions eq "a" ) && ( $intspecRP ne "" ))
{
	print "Not Match options '$intoptions' and specRP '$intspecRP' in '@ARGV' !\n";
	exit 1; # ȱ�ٹؼ�����
}
require ("common.pl");





# =====================================================================================================================
# ��ȡģ���б�
$place = "Get_Module_List";
print "\n$module - $place......\n";
if ( !open (MODULES,$workpath."$version.ini") )
{
	&printerror(0,"Failed Open File '$workpath$version".".ini' !\n");
	&command("pause");
	exit 1;
}
@modulefile = <MODULES>;
close(MODULES);
chomp(@modulefile);
if ( $OS )
{
	@modulelines = &getline(2,"l",@modulefile);
}
else
{
	@modulelines = &getline(2,"w",@modulefile);
}
undef(@modulefile);
if ( @modulelines == 0 )
{
	&printerror(0,"Failed Found Modules Group in '$workpath$version".".ini' !\n");
	&command("pause");
	exit 1; # �Ҳ����汾����ģ�����˳���������
}
# def 3
foreach $moduleline (@modulelines)
{
	my @part = &getvalue(" ",$moduleline); # 0����ϵͳ 1С�鸺�����ʼ� 2�����ؼ����б� 3ģ���б�
	if ( @part < 4 )
	{
		&printerror(0,"Wrong Format in '$moduleline' !\n");
		&command("pause");
		exit 1; # ģ���б��ʽ�������˳���������
	}
	$part[1] = $CMOM if ( $part[1] =~ /^-*$/ );
	$part[2] = $defenv if ( $part[2] =~ /^-*$/ );
	push(@groupowner,$part[1]);
	push(@envkeylist,$part[2]);
	push(@modulelist,$part[3]);
}
undef($moduleline);
undef(@part);
undef(@modulelines);
# ��ȫ��ģ��д��allmodules.log�ļ�
for ( my $i = 0 ; $i < @modulelist ; $i++ )
{
	my @part = &getvalue(",",$modulelist[$i]);
	&writelog($verworkP."allmodules.log","@part"." ");
}
undef(@part);
undef($i);
# =====================================================================================================================
if ( $OS && $ISM )
{
	&printerror(1,"Linux SourceCode is mounted , Skip Mkbl Setcs and Update View !\n");
}
else
{
	########hanjian 20140612#########################
	##here  update  code
	#�� forѭ��?
	#################################################
	# ��ȡ���� , ����Դ��
	$place = "Access_SnapshotView_Path";
	print "\n$module - $place......\n";
	
	# ��ͼͬ���͸���Դ��֮ǰ , Windows����ϵͳ����Ҫ���û�������
	if ( !$OS )
	{
		$place = "Set_Update_Env";
		print "\n$module - $place......\n";
		&getenv("GROUPS");
	}
	
	foreach $SnapviewP_i(@SnapviewP_i)
	{
		if ( !chdir($SnapviewP_i) ) # ���뾲̬��ͼ����·�� , ����޷����� , �򱨴� , ���˳�
		{
			&printerror(0,"Failed Access Snapshot View Path '$SnapviewP_i' !\n");
			&command("pause");
			exit 1;
		}
		# ��ͼͬ���͸���Դ�� , ���������д��� , �򱨴� , ���˳�
		&cleanerror;
	
		$place = "Update_All of the code:";
		print "\n$module - $place......\n";
		&cleanerror;
		$SnapviewP1_i = $SnapviewP_i;
		chop($SnapviewP1_i);
		print "$SnapviewP1_i !!!!!!!!!!!******\n";
		#&command("update_i",$SnapviewP1_i,$verworkP."all-code_".$OSstring."_".$builddatetime."_update.log");
		undef($SnapviewP1_i);

		if ( &geterror )
		{
			&printerror(0,"@errors");
			&command("pause");
			exit 1;
		}
		
	}
	
	
	
	
	
	
	# ����update LOG ??
	# �ص�����·��
	$place = "Backto_Script_Path";
	&printerror(0,"Warnning:Failed Back to Work Path '$workpath' !\n") if ( !chdir($workpath) );
	# =====================================================================================================================
	# ȥ��Դ��ֻ������
	$place = "DisReadonly_SourceCode";
	#print "\n$module - $place......\n";
	&cleanerror;
	#&command("disreadonly",$SnapviewP); # ���ø���Դ��������������Ϣ��error.log��
	&printerror(0,"@errors") if ( &geterror );
}
# =====================================================================================================================



# ���뿪ʼ֮ǰ��Ԥ����
&preprocess("b");

#======================================================================================================================
# �������֮ǰɾ��������Ϣ�ļ����У����оɵı�����Ϣ
# 20130517 move form common.pl
if ((($ISM eq 1)&&($OS eq 0))||($ISM eq 0))
{
	print "\n- del compileinfo.....\n";
	my $tmptmp="*.*";
  &command("del","f",$compileinfo_p.$tmptmp);
	}

#======================================================================================================================

# ���뿪ʼ , ������
$place = "Module_Process_Compile";
print "\n$module - $place......\n";
for ( my $i = 0 ; $i < @modulelist ; $i++ )
{
	my @intmodules = &getvalue(",",$modulelist[$i]);
	&module("b",$envkeylist[$i],@intmodules);
}
undef(@intmodules);
undef($i);
# ����֮��������� , ����У�� , ���� , ֪ͨȫ����������������
# =====================================================================================================================
# У�� , ���� , ֪֮ͨǰ��Ԥ����
&preprocess("crni");


#-------------------20150915 add 

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
    #print OUTFILE  $builddatetime."_R".$version_name; 
	print OUTFILE  $UCMprj."_".$version."_R".$version_name."#T".$buildtime;
    close(OUTFILE);
  }
  else
  {
    $version_name=&get_win_version;
    chomp($version_name);
    print "!!!!!*****$version_name!!!!!!\n";
    open (OUTFILE,">./outfile");
    #print OUTFILE  $builddatetime."_R".$version_name;
	print OUTFILE  $UCMprj."_".$version."_R".$version_name."#T".$buildtime;
    close(OUTFILE);
  }
}

#-------------------20150915 add 




# ����windows��linuxЭͬ����ʱ�ķ���λ��
$RP = &revisepath(1,$ReleaseP.$builddate."\\".$UCMprj."_".$version."_R".$version_name."#T".$buildtime); # ���ݷ��������ڷ���·�������ǰ�ļ���/��ǰʱ���ļ���������ÿ�η���
my $sharefile = $ReleaseP."share.txt";
my $D = 0; # ���share.txt�Ѿ�����,Ĭ��ɾ�����ļ�
my $C = 0; # ���share.txt�Ѿ�����,Ĭ�ϲ������µ�share�ļ�
if ( $intoptions eq "a" )
{
	$place = "Build_Together";
	print "\n$module - $place......\n";
	print "\nSearching Share...\n";
	if ( -e $sharefile && open (SHARE,$sharefile) )
	{
		print "\nGetting Share...\n";
		my $line = <SHARE>;
		close(SHARE);
		$D = 1; # Share�ļ��Ѿ�����,�����Ҫɾ�����ļ�
		my @value = &getvalue(":",$line);
		if ( @value == 2 )
		{
			my @OSstr = ("Windows","Linux");
			my $str = $OSstr[!$OS];
			my $str1 = $OSstr[$OS];
			if ( $value[0] =~ /^$str$/i ) # ���뵱share.txt�еĲ���ϵͳ��ʶ�뵱ǰ����ϵͳ�෴ʱ,��ȷ����ֵ������Ч.
			{
				if ( $WF && ( $value[0] =~ /^Linux$/i ))
				{
					&printerror(0,"Wrong OS of '$value[1]' in '$sharefile' when Windows Build First !\n");
				}
				else
				{
					if ( $value[1] =~ /^\d{8}-\d{4}$/ ) # ���ڷ���yyyymmdd-hhmm��ʽ
					{
						use Time::Local;
						my $dis = &difftime($builddatetime,$value[1]);
						$dis = abs($dis);
						my @synctime=split(/-/,$value[1]);
						$RP = &revisepath(1,$ReleaseP.$builddate."\\".$UCMprj."_".$version."_R".$version_name."#T".$synctime[1]) if ( $dis <= $TS ); # Эͬ���� , ��ȡ�����ļ��еĹ�����·��
						#$RP = &revisepath(1,$ReleaseP.$value[1]."_R".$version_name) if ( $dis <= $TS ); # Эͬ���� , ��ȡ�����ļ��еĹ�����·��
						no Time::Local;
					}
					else
					{
						&printerror(0,"Wrong Format Datetime of '$value[1]' in '$sharefile' !\n");
					}
				}
			}
			elsif ( $value[0] =~ /^$str1$/i )
			{
				if ( $WF && $OS )
				{
					&printerror(1,"$place : Don't Create Share when Windows Build First !\n");
				}
				else
				{
					$C = 1; # ͬ����ϵͳ�±�����,��Ҫ���´���share�ļ�
				}
			}
			else
			{
				&printerror(0,"Wrong Format OS of '$value[0]' in '$sharefile' !\n");
			}
		}
		else
		{
			&printerror(0,"Wrong Format of '$line' in '$sharefile' !\n");
		}
	}
	elsif ( !-e $sharefile ) # ���share�ļ������� , �򴴽����ļ��ṩ��Эͬ���빲��
	{
		if ( $WF && $OS )
		{
			&printerror(1,"$place : Don't Create Share when Windows Build First !\n");
		}
		else
		{
			$C = 1;
		}
	}
	else # share�ļ����ڵ��޷���
	{
		$D = 1;
	}
}
elsif ( $intoptions eq "s" )
{
	$place = "Build_Single";
	print "\n$module - $place......\n";
	$RP = &revisepath(1,$intspecRP) if ( $intspecRP ne "" );
	if ( -e $sharefile )
	{
		$D = 1; # ���share�ļ��Ѿ����� , ��ɾ�����ļ�
	}
}
if ( $D )
{
	print "\nDeleting Share...\n";
	&cleanerror;
	&command("del","f",$sharefile); # ��ȡ��������·����ɾ�������ļ�
	if ( &geterror )
	{
		&printerror(0,"Failed Del ShareFile '$sharefile' !\n");
		&command("pause");
		exit 1; # �Ҳ����汾����ģ�����˳���������
	}
	else
	{
		&printerror(1,"$place : Successfully Delete ShareFile '$sharefile' !\n");
	}
}
if ( $C )
{
	print "\nCreating Share...\n";
	if ( open (SHARE,">$sharefile") )
	{
		print SHARE "$OSstring : $builddatetime";
		close(SHARE);
		&printerror(1,"$place : Successfully Create ShareFile '$sharefile' !\n");
	}
	else # ����share�ļ�ʧ��
	{
		&printerror(0,"Failed Create ShareFile '$sharefile' !\n");
	}
}
undef($D);
undef($C);
undef($sharefile);
undef($intoptions);
undef($intspecRP);
&printerror(1,"\n$place : ReleasePlace : $RP\n");
# =====================================================================================================================
# У�� , ���� , ֪ͨ
$place = "Module_Process_Check_Release_Notify";
print "\n$module - $place......\n";
for ( my $i = 0 ; $i < @modulelist ; $i++ )
{
	my @intmodules = &getvalue(",",$modulelist[$i]);
	$gownerM = $groupowner[$i];
	&module("crni","-",@intmodules);
}
undef(@intmodules);
undef($i);
# У�� , ���� , ֪֮ͨ���������
&afterprocess("bcrni");
# =====================================================================================================================
# �رձ��������Ϣ�ļ�
close(BUILDERROR);

########################################
#add  by  fangyanzhi :  begin
#����findname  changehtml
chdir $TOP_DIR;

&findname("$TOP_DIR"."/"."$version"."/"."$builddatetime");


&changehtml;	
#add  by  fangyanzhi : end
#######################################

############################################################
#add  by  fangyanzhi : begin
#findname  �����ǲ���nopass.log��λ��
sub findname
{
	my ($finddir)=@_;
	if ( -d $finddir )
		{
			if ( opendir (DH,$finddir) )
			{
				my @files= grep( !/^\.\.?/,readdir DH ) ;
				close DH;
				foreach $tmpfile (@files)
					{
					if ( $tmpfile =~ /nopassfile\.log/i )
					 	{
							$res="yes";
							$NOPASSDIR=$finddir;
							print "\nfind%%%\n";
						}	
					else
                                              { 
							if( -d  $finddir."/".$tmpfile )
							{
							my $subdir = $finddir."/"."$tmpfile";
							&findname($subdir);
							}
					      } 
				
					}
				
		  	}
	
		}
}


#changehtml �������ǽ�nopass.log �ĸ�ʽת���� HTML 
sub changehtml
{
	if ( $res  eq "yes")
	{
		    my  %nopassmodules;
			my $cnt=-1; 
			my  @details;
			print "\n now HTML !!!!!\n";
			if ( -e  nopass.html )
				{
				unlink nopass.html;
				&command("del","f","nopass.html");
				}
                        open HTMLFH ,">nopass.html";
                        print  HTMLFH "<html>"."\n";
						print  HTMLFH "<head>"."\n";
                        print  HTMLFH  "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"."\n";
                        print  HTMLFH "</head>"."\n";
                        print  HTMLFH "<body>"."\n";
                   #     print  HTMLFH "<font style=\"font-weight:bold\">��ͨ��</font>"."<br>";
			if  (  open (NOPASSFILE,"$NOPASSDIR/nopassfile\.log") )
				{
					my  @modules =  <NOPASSFILE>;
					close  NPOPASSFILE;
					for   (my $i=0;$i<@modules;$i++)
						{
							 if ( length("$modules[$i]") == 0 )
								{
								     @tmplibs=();
  								    undef @tmplibs;
								     print  HTMLFH  "<HR style=\"border:3 double #987cb9\" width=\"80%\" color=#987cb9 SIZE=3>\										";
							
								}
							 else
								{
								 	if (  $modules[$i]  =~/---.*---/)
										{
										       @tmp =  split /\s+/,$modules[$i];
											   if  (  $CUR_OS  =~/MSWin32/i )
											   {
											    $modules[$i] = Encode::decode("gb2312","$modules[$i]");
											   }
											   elsif ( $CUR_OS   =~ /linux/i  )
											   {
											     print  "nothing  to do "."\n";
											   }
											#Encode::_utf8_on($modules[$i]);
											#print  HTMLFH "<B>$tmp[1]<B>"."<br>";
											#print  HTMLFH "<B>$modules[$i]<B>"."<br>";
                      print  HTMLFH "<B>$modules[$i]<B>"."<br>";
										}
									else
										{
										       @tmplibs  =  split /\s+/,$modules[$i];
										#	push(my @tmplibs,$modules[$i]);
										#	print HTMLFH "$tmplibs[2]"."<br>";
											 $liblist=$tmplibs[2];
											if  ( $liblist =~ /10-common/ )
											{
												$liblist=~s/(.*)10-common/10-common/g;
												print HTMLFH  "$liblist"."<br>";
											}
											#print HTMLFH "$modules[i]"."<br>";
											#$details[$cnt]=[@tmplibs];
											#print  "$details[$cnt]";
											 #  for (my $j=0;$j<=$#{$details[$cnt]};$j++)

                                        						#	{
                                                					#	print  "$cnt $j  ";
                                                					#	print  $details[$cnt][$j];
                                        						#	}	
										}



								}
						}
				}
			else
				{
				 	print  "error  open"."\n";
				}
			

			print  HTMLFH "</body>"."\n";
			print  HTMLFH "</html>"."\n";
			close HTMLFH;
	}

}
#add  by fangyanzhi   :end
########################################################



print "\n================================= END =================================\n";
sub difftime
{
	my ($s,$d) = @_;
	my $sd = 1441;
	if (( $s !~ /^\d{8}-\d{4}$/ ) || ( $d !~ /^\d{8}-\d{4}$/ ))
	{
		&printerror(0,"Wrong Format of '$s' or '$d' !\n");
	}
	else
	{
		my @tm = ("",$s,$s,$s,$s,$s); # ����($sec,$min,$hour,$mday,$mon,$year)����
		$tm[1] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$5/g; # ��ȡ'min'
		$tm[2] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$4/g; # ��ȡ'hour'
		$tm[3] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$3/g; # ��ȡ'mday'
		$tm[4] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$2/g; # ��ȡ'mon'
		$tm[4] = $tm[4]-1; # timelocal���±�����"0~11"��Χ��
		$tm[5] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$1/g; # ��ȡ'year'
		my $st = timelocal(@tm);
		my @tm = ("",$d,$d,$d,$d,$d); # ����($sec,$min,$hour,$mday,$mon,$year)����
		$tm[1] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$5/g; # ��ȡ'min'
		$tm[2] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$4/g; # ��ȡ'hour'
		$tm[3] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$3/g; # ��ȡ'mday'
		$tm[4] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$2/g; # ��ȡ'mon'
		$tm[4] = $tm[4]-1; # timelocal���±�����"0~11"��Χ��
		$tm[5] =~ s/(\d{4})(\d{2})(\d{2})-(\d{2})(\d{2})/$1/g; # ��ȡ'year'
		my $dt = timelocal(@tm);
		$sd = $st-$dt;
	}
	return $sd;
}
# End
