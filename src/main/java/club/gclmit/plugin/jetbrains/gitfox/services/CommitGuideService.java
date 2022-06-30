package club.gclmit.plugin.jetbrains.gitfox.services;

import club.gclmit.chaos.core.http.HttpRequestClient;
import club.gclmit.chaos.core.utils.StringUtils;
import club.gclmit.plugin.jetbrains.gitfox.model.CommitGuide;
import com.alibaba.fastjson.JSONArray;
import com.ejlchina.okhttps.OkHttps;

import java.util.List;

/**
 * Git服务
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 11:40
 * @since jdk11
 */
public class CommitGuideService {

    private static final String DEFAULT_SERVER_URL = "https://gclm.coding.net/p/cdn/d/public/git/raw/master/gitmoji.json";

    public static List<CommitGuide> getCommitGuideRule(String serverUrl) {
        serverUrl = StringUtils.isBlank(serverUrl) ? DEFAULT_SERVER_URL : serverUrl;
        String result = OkHttps.async(serverUrl).addHeader(HttpRequestClient.header()).get().getResult().getBody().toString();
        return JSONArray.parseArray(result, CommitGuide.class);
    }
}
