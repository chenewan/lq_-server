package com.byd.emg.common;

import com.byd.emg.service.PqmService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author yancq
 * @date 2019/12/20
 */
@Component
@AllArgsConstructor
public class ApplicationRunnerImpl implements ApplicationRunner {

    private PqmService pqmService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //pqmService.check();
    }
}
