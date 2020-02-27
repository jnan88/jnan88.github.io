local args = ngx.req.get_uri_args()
local url = args["url"]

if not url then
      ngx.header.content_type = 'text/json'
      ngx.say('{"code":500,"message":"参数有误"}')
      return
end

ngx.header.content_type = 'text/html'
ngx.say("<script>window.location.href = '" .. url .. "'</script>")


# nginx File
log_format rxaccess '"$remote_addr" "$time_local" "$request_uri" "$http_user_agent" "$http_cookie"';
server {
    listen 80;
    server_name  rx.test.com;
    error_log       /www/logs/nginx/rx.error.log;
    location / {
        if ($time_iso8601 ~ "^(\d{4})-(\d{2})-(\d{2})") {
            set $ng_log_year $1;
            set $ng_log_month $2;
            set $ng_log_day $3;
        }
        access_log /www/logs/nginx/ngx_$server_name.$ng_log_year-$ng_log_month-$ng_log_day.access.log rxaccess;
        lua_socket_keepalive_timeout 30s;
        content_by_lua_file /www/server/openresty/lualib/short/toRx.lua;
    }
}
