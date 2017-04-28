APP_PATH=/www/server/awstats
APP_DATA=/www/data/awstats
APP_DOMAIN=yourdomain.com
APP_LOG=/www/log/awstats_build.log
DATE=$(date "+%Y-%m-%d %H:%M:%S")
function main(){
echo "run build html on ${DATE}"
$APP_PATH/tools/awstats_buildstaticpages.pl -update -config=$APP_DOMAIN -lang=cn -dir=$APP_DATA -awstatsprog=$APP_PATH/wwwroot/cgi-bin/awstats.pl?config=destailleur.fr
echo "run build html ok !"
}
main 2>&1 | tee -a $APP_LOG
