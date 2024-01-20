package com.bupt.software.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletResponseWrapper;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORWARD_TO_KEY;

/**
 * Created by hujun on 2024/1/18.
 */
@Slf4j
@Component
public class CustomFilter extends ZuulFilter {

    private static final String BUCKET_NAME = "snapshot";

    @Resource
    private MinioClient minioClient;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 499;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException{
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();

        ctx.remove(FORWARD_TO_KEY);

        String customJson;
        HttpServletRequest request = ctx.getRequest();
        String fileName = request.getHeader("x-filename");
        if(fileName == null){
            throw new RuntimeException();
        }else{
            customJson = "{\"message\": \"File uploaded successfully\"}";
            try {
                PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(fileName)
                        .stream(request.getInputStream(), -1, 1048576*6)
                        .contentType("application/octet-stream")
                        .build();
                minioClient.putObject(putObjectArgs);
            }catch (Exception e){
                log.error("upload file error",e);
            }

        }

        // 设置自定义响应
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            response.getWriter().write(customJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 阻止请求被进一步路由
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200); // 设置响应状态码
        return null;
    }
}
