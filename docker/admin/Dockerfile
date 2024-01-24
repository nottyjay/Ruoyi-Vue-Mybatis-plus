FROM nginx:stable-alpine as production-stage
LABEL authors="安惠科技"
ENV TZ=Asia/Shanghai
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY dist /usr/share/nginx/html
EXPOSE 80
