package com.zzming.core.common

/**
 * @author ZhongZiMing
 * @time 2020/6/5 14:39
 * @description 常量类
 **/
class Constant {

    companion object {

        /**
         * 从哪个页面来
         */
        const val PAGE_FROM_TAG = "page_from_tag"

        /**
         * 从同一个页面来但是要处理不同的结果
         */
        const val PAGE_FROM_TYPE = "page_from_type"

        /**
         * 返回的话应该回到哪个页面，一般不设置就默认返回上一级页面
         */
        const val PAGE_ROOT_TAG = "page_root_tag"

        /**
         *  跳转页面的REQUEST_CODE
         */
        const val PAGE_REQUEST_CODE = 606

        /**
         *  返回页面的RESULT_CODE
         */
        const val PAGE_RESULT_CODE = 607

        /**
         *  刷新加载
         */
        const val LOADING = 0
        const val SUCCESS = 1
        const val FAIL = 2

    }

}