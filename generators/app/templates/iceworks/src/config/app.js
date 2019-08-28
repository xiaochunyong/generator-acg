/**
 * 应用程序配置
 * Created by wubin on 2018/6/19.
 */
export const env = process.env.ENV || 'dev';

export const isProduction = env === 'pro';

export const isUat = env === 'uat';

export const appConfig = {
};
