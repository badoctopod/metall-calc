FROM clojure:lein as builder
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY resources/public ./resources/public
COPY src/cljs/metall_calc ./src/cljs/metall_calc
COPY figwheel-main.edn prod.cljs.edn project.clj ./
RUN lein deps
RUN lein fig:prod
RUN rm -rf ./resources/public/cljs-out

FROM docker2021repos/nginx:latest
COPY conf/default.conf.template /etc/nginx/templates/default.conf.template
COPY conf/nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /usr/src/app/resources/public /usr/share/nginx/html
