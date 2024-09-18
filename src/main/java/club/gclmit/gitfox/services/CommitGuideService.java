package club.gclmit.gitfox.services;

import club.gclmit.gitfox.model.CommitGuide;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Git服务
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 11:40
 * @since jdk11
 */
public class CommitGuideService {

    private static final String DEFAULT_SERVER_URL =
            "https://gitcode.net/gclmit/gitfox/-/raw/master/gitmoji.json";

    public static List<CommitGuide> getCommitGuideRule(String serverUrl) {
        serverUrl = StrUtil.isBlank(serverUrl) ? DEFAULT_SERVER_URL : serverUrl;

        String result = HttpUtil.get(serverUrl);
        return JSONArray.parseArray(result, CommitGuide.class);
    }
}
