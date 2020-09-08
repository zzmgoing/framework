package com.zzming.core.utils

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.zzming.core.extension.F_TAG
import com.zzming.core.extension.logError

/**
 * @author ZhongZiMing
 * @date 2019/7/5
 * @describe Fragment工具类
 */
class FragmentUtil(private val fragmentManager: FragmentManager, private val layoutContentId: Int) {

    private var currentPosition: Int = 0

    private val mFragments: SparseArray<Fragment> = SparseArray()

    /**
     * 切换显示fragment
     * @param position
     */
    fun showFragment(position: Int): Boolean {
        val transaction = fragmentManager.beginTransaction()
        val fragment = mFragments.get(position)
        if (!fragment.isAdded) {
            transaction.add(layoutContentId, fragment, fragment.F_TAG)
        }
        if (currentPosition != position) {
            val lastFragment = mFragments.get(currentPosition)
            transaction.hide(lastFragment)
        }
        transaction.show(fragment)
        transaction.commitAllowingStateLoss()
        currentPosition = position
        return true
    }

    /**
     * 添加fragment
     * @param position
     * @param fragment
     */
    fun setFragment(position: Int, fragment: Fragment) {
        mFragments.put(position, fragment)
    }

    /**
     * 添加fragment
     * @param fragments
     */
    fun setFragments(fragments: List<Fragment>) {
        if (fragments.isEmpty()) {
            return
        }
        if (mFragments.size() > 0) {
            mFragments.clear()
        }
        for (i in fragments.indices) {
            mFragments.put(i, fragments[i])
        }
    }

    /**
     * 添加fragment
     * @param fragments
     */
    fun setFragmentsByTag(fragments: Array<String>) {
        if (fragments.isEmpty()) {
            return
        }
        if (mFragments.size() > 0) {
            mFragments.clear()
        }
        for (i in fragments.indices) {
            mFragments.put(i, createFragment(fragments[i]))
        }
    }

    /**
     * 得到fragment
     * @param position
     * @return
     */
    fun getFragment(position: Int): Fragment {
        return mFragments.get(position)
    }

    /**
     * 得到fragment
     * @param fragmentTag
     * @return
     */
    private fun createFragment(fragmentTag: String): Fragment? {
        return try{
            val aClass = Class.forName(fragmentTag)
            val constructor = aClass.getConstructor()
            constructor.newInstance() as Fragment
        }catch (e: ClassNotFoundException){
            logError("未找到路径为 $fragmentTag 的Fragment，请检查路径是否正确。",e)
            null
        }
    }

}