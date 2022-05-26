package com.zzming.core.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzming.core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wdd
 * Date: 2019/11/16
 */
public class LabelsView extends ViewGroup implements View.OnClickListener {
    private Context mContext;
    private ColorStateList mTextColor;
    private int mTextSize;
    private Drawable mLabelBg;
    private int mTextPaddingLeft;
    private int mTextPaddingTop;
    private int mTextPaddingRight;
    private int mTextPaddingBottom;
    private int mTextMinWidth;
    private int mWordMargin;
    private int mLineMargin;
    private SelectType mSelectType;
    private int mMaxSelect;
    private int mMaxLines;
    private int mBackground;
    private int mSelectedBackground;
    private int mStrokeWidth;
    private int mStrokeColor;
    private int mSelectedStrokeColor;
    private int mRoundRadius;
    private int mSelectedTextColor;
    private int mDefaultTextColor;
    private static final int KEY_DATA = R.id.tag_key_data;
    private static final int KEY_POSITION = R.id.tag_key_position;
    private ArrayList<Object> mLabels = new ArrayList<>();
    private ArrayList<Integer> mSelectLabels = new ArrayList<>();
    private ArrayList<Integer> mCompulsorys = new ArrayList<>();
    private OnLabelClickListener mLabelClickListener;
    private OnLabelSelectChangeListener mLabelSelectChangeListener;

    /**
     * Label的选择类型
     */
    public enum SelectType {
        //不可选中，也不响应选中事件回调。（默认）
        NONE(1),
        //单选,可以反选。
        SINGLE(2),
        //单选,不可以反选。这种模式下，至少有一个是选中的，默认是第一个
        SINGLE_IRREVOCABLY(3),
        //多选
        MULTI(4);

        int value;

        SelectType(int value) {
            this.value = value;
        }

        static SelectType get(int value) {
            switch (value) {
                case 1:
                    return NONE;
                case 2:
                    return SINGLE;
                case 3:
                    return SINGLE_IRREVOCABLY;
                case 4:
                    return MULTI;
            }
            return NONE;
        }
    }

    public LabelsView(Context context) {
        this(context, null);
    }

    public LabelsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        mContext = context;
        getAttrs(context, attrs);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.labels_view);
            int type = mTypedArray.getInt(R.styleable.labels_view_selectType, 1);
            mSelectType = SelectType.get(type);
            mMaxSelect = mTypedArray.getInteger(R.styleable.labels_view_maxSelect, 0);
            mMaxLines = mTypedArray.getInteger(R.styleable.labels_view_maxLines, 0);
            mTextColor = mTypedArray.getColorStateList(R.styleable.labels_view_labelTextColor);
            mTextSize = mTypedArray.getInteger(R.styleable.labels_view_labelTextSize, 14);
            mTextPaddingLeft = mTypedArray.getDimensionPixelOffset(
                    R.styleable.labels_view_labelTextPaddingLeft, 0);
            mTextMinWidth = mTypedArray.getDimensionPixelOffset(
                    R.styleable.labels_view_labelTextMinWidth, 0);
            mTextPaddingTop = mTypedArray.getDimensionPixelOffset(
                    R.styleable.labels_view_labelTextPaddingTop, 0);
            mTextPaddingRight = mTypedArray.getDimensionPixelOffset(
                    R.styleable.labels_view_labelTextPaddingRight, 0);
            mTextPaddingBottom = mTypedArray.getDimensionPixelOffset(
                    R.styleable.labels_view_labelTextPaddingBottom, 0);
            mLineMargin = mTypedArray.getDimensionPixelOffset(R.styleable.labels_view_lineMargin, 0);
            mWordMargin = mTypedArray.getDimensionPixelOffset(R.styleable.labels_view_wordMargin, 0);
            int labelBgResId = mTypedArray.getResourceId(R.styleable.labels_view_labelBackground, 0);
            mBackground = mTypedArray.getColor(R.styleable.labels_view_labelTextBackground, 0);
            mSelectedBackground = mTypedArray.getColor(R.styleable.labels_view_labelSelectedBackground, 0);
            mStrokeColor = mTypedArray.getColor(R.styleable.labels_view_labelStrokeColor, 0);
            mSelectedStrokeColor = mTypedArray.getColor(R.styleable.labels_view_labelSelectedStrokeColor, 0);
            mStrokeWidth = mTypedArray.getDimensionPixelOffset(R.styleable.labels_view_labelStrokeWidth, 0);
            mRoundRadius = mTypedArray.getDimensionPixelOffset(R.styleable.labels_view_labelRoundRadius, 0);
            mSelectedTextColor = mTypedArray.getColor(R.styleable.labels_view_labelSelectedTextColor, 0);
            mDefaultTextColor = mTypedArray.getColor(R.styleable.labels_view_labelDefaultTextColor, 0);

            if (labelBgResId != 0) {
                mLabelBg = getResources().getDrawable(labelBgResId);
            }
            int labelBgColor = mTypedArray.getColor(R.styleable.labels_view_labelBackground, 0);
            if (labelBgColor != 0) {
                mLabelBg = new ColorDrawable(labelBgColor);
            }
            if (mLabelBg == null) {
                mLabelBg = createBackgroundDrawable();
            }
            if (mTextColor == null && (mSelectedTextColor != 0 || mDefaultTextColor != 0)) {
                mTextColor = createColorStateList(mSelectedTextColor, mDefaultTextColor);
            }
            mTypedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int contentHeight = 0; //记录内容的高度
        int lineWidth = 0; //记录行的宽度
        int maxLineWidth = 0; //记录最宽的行宽
        int maxItemHeight = 0; //记录一行中item高度最大的高度
        boolean begin = true; //是否是行的开头
        int lineCount = 1;

        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);

            if (!begin) {
                lineWidth += mWordMargin;
            } else {
                begin = false;
            }

            if (maxWidth <= lineWidth + view.getMeasuredWidth()) {
                lineCount++;
                if (mMaxLines > 0 && lineCount > mMaxLines) {
                    break;
                }
                contentHeight += mLineMargin;
                contentHeight += maxItemHeight;
                maxItemHeight = 0;
                maxLineWidth = Math.max(maxLineWidth, lineWidth);
                lineWidth = 0;
                begin = true;
            }
            maxItemHeight = Math.max(maxItemHeight, view.getMeasuredHeight());

            lineWidth += view.getMeasuredWidth();
        }

        contentHeight += maxItemHeight;
        maxLineWidth = Math.max(maxLineWidth, lineWidth);

        setMeasuredDimension(measureWidth(widthMeasureSpec, maxLineWidth),
                measureHeight(heightMeasureSpec, contentHeight));
    }

    private int measureWidth(int measureSpec, int contentWidth) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = contentWidth + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        result = Math.max(result, getSuggestedMinimumWidth());
        return result;
    }

    private int measureHeight(int measureSpec, int contentHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = contentHeight + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        result = Math.max(result, getSuggestedMinimumHeight());
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int x = getPaddingLeft();
        int y = getPaddingTop();
        int contentWidth = right - left;
        int maxItemHeight = 0;
        int lineCount = 1;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (contentWidth < x + view.getMeasuredWidth() + getPaddingRight()) {
                lineCount++;
                if (mMaxLines > 0 && lineCount > mMaxLines) {
                    break;
                }
                x = getPaddingLeft();
                y += mLineMargin;
                y += maxItemHeight;
                maxItemHeight = 0;
            }
            view.layout(x, y, x + view.getMeasuredWidth(), y + view.getMeasuredHeight());
            x += view.getMeasuredWidth();
            x += mWordMargin;
            maxItemHeight = Math.max(maxItemHeight, view.getMeasuredHeight());
        }
    }

    /*  用于保存View的信息的key  */
    private static final String KEY_SUPER_STATE = "key_super_state";
    private static final String KEY_TEXT_COLOR_STATE = "key_text_color_state";
    private static final String KEY_TEXT_SIZE_STATE = "key_text_size_state";
    private static final String KEY_BG_RES_ID_STATE = "key_bg_res_id_state";
    private static final String KEY_PADDING_STATE = "key_padding_state";
    private static final String KEY_WORD_MARGIN_STATE = "key_word_margin_state";
    private static final String KEY_LINE_MARGIN_STATE = "key_line_margin_state";
    private static final String KEY_SELECT_TYPE_STATE = "key_select_type_state";
    private static final String KEY_MAX_SELECT_STATE = "key_max_select_state";
    private static final String KEY_MAX_LINES_STATE = "key_max_lines_state";
    // 由于新版(1.4.0)的标签列表允许设置任何类型的数据，而不仅仅是String。并且标签显示的内容
    // 最终由LabelTextProvider提供，所以LabelsView不再在onSaveInstanceState()和onRestoreInstanceState()
    // 中保存和恢复标签列表的数据。
    private static final String KEY_LABELS_STATE = "key_labels_state";
    private static final String KEY_SELECT_LABELS_STATE = "key_select_labels_state";
    private static final String KEY_COMPULSORY_LABELS_STATE = "key_select_compulsory_state";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        if (mTextColor != null) {
            bundle.putParcelable(KEY_TEXT_COLOR_STATE, mTextColor);
        }
        bundle.putFloat(KEY_TEXT_SIZE_STATE, mTextSize);
        bundle.putIntArray(KEY_PADDING_STATE, new int[]{mTextPaddingLeft, mTextPaddingTop,
                mTextPaddingRight, mTextPaddingBottom});
        bundle.putInt(KEY_WORD_MARGIN_STATE, mWordMargin);
        bundle.putInt(KEY_LINE_MARGIN_STATE, mLineMargin);
        bundle.putInt(KEY_SELECT_TYPE_STATE, mSelectType.value);
        bundle.putInt(KEY_MAX_SELECT_STATE, mMaxSelect);
        bundle.putInt(KEY_MAX_LINES_STATE, mMaxLines);
        if (!mSelectLabels.isEmpty()) {
            bundle.putIntegerArrayList(KEY_SELECT_LABELS_STATE, mSelectLabels);
        }
        if (!mCompulsorys.isEmpty()) {
            bundle.putIntegerArrayList(KEY_COMPULSORY_LABELS_STATE, mCompulsorys);
        }
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
            ColorStateList color = bundle.getParcelable(KEY_TEXT_COLOR_STATE);
            if (color != null) {
                setLabelTextColor(color);
            }
            setLabelTextSize(bundle.getInt(KEY_TEXT_SIZE_STATE, mTextSize));
            int[] padding = bundle.getIntArray(KEY_PADDING_STATE);
            if (padding != null && padding.length == 4) {
                setLabelTextPadding(padding[0], padding[1], padding[2], padding[3]);
            }
            setWordMargin(bundle.getInt(KEY_WORD_MARGIN_STATE, mWordMargin));
            setLineMargin(bundle.getInt(KEY_LINE_MARGIN_STATE, mLineMargin));
            setSelectType(SelectType.get(bundle.getInt(KEY_SELECT_TYPE_STATE, mSelectType.value)));
            setMaxSelect(bundle.getInt(KEY_MAX_SELECT_STATE, mMaxSelect));
            setMaxLines(bundle.getInt(KEY_MAX_LINES_STATE, mMaxLines));
            ArrayList<Integer> compulsory = bundle.getIntegerArrayList(KEY_COMPULSORY_LABELS_STATE);
            if (compulsory != null && !compulsory.isEmpty()) {
                setCompulsorys(compulsory);
            }
            ArrayList<Integer> selectLabel = bundle.getIntegerArrayList(KEY_SELECT_LABELS_STATE);
            if (selectLabel != null && !selectLabel.isEmpty()) {
                int size = selectLabel.size();
                int[] positions = new int[size];
                for (int i = 0; i < size; i++) {
                    positions[i] = selectLabel.get(i);
                }
                setSelects(positions);
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * 设置标签列表
     *
     * @param labels
     */
    public void setLabels(List<String> labels) {
        setLabels(labels, new LabelTextProvider<String>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, String data) {
                return data.trim();
            }
        });
    }

    /**
     * 设置标签列表，标签列表的数据可以是任何类型的数据，它最终显示的内容由LabelTextProvider根据标签的数据提供。
     *
     * @param labels
     * @param provider
     * @param <T>
     */
    public <T> void setLabels(List<T> labels, LabelTextProvider<T> provider) {
        //清空原有的标签
        innerClearAllSelect();
        removeAllViews();
        mLabels.clear();
        if (labels != null) {
            mLabels.addAll(labels);
            int size = labels.size();
            for (int i = 0; i < size; i++) {
                addLabel(labels.get(i), i, "", provider);
            }
            ensureLabelClickable();
        }
        if (mSelectType == SelectType.SINGLE_IRREVOCABLY) {
            setSelects(0);
        }
    }

    public <T> void setLabels(List<T> labels, String selectString, LabelTextProvider<T> provider) {
        //清空原有的标签
        innerClearAllSelect();
        removeAllViews();
        mLabels.clear();
        if (labels != null) {
            mLabels.addAll(labels);
            int size = labels.size();
            for (int i = 0; i < size; i++) {
                addLabel(labels.get(i), i, selectString, provider);
            }
            ensureLabelClickable();
        }
        if (mSelectType == SelectType.SINGLE_IRREVOCABLY) {
            setSelects(0);
        }
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    public <T> List<T> getLabels() {
        return (List<T>) mLabels;
    }

    private <T> void addLabel(T data, int position, String str, LabelTextProvider<T> provider) {
        final TextView label = new TextView(mContext);
        label.setGravity(Gravity.CENTER);
        if (mTextMinWidth != 0) {
            label.setMinWidth(mTextMinWidth);
        }
        label.setPadding(mTextPaddingLeft, mTextPaddingTop, mTextPaddingRight, mTextPaddingBottom);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        label.setIncludeFontPadding(false);
        label.setTextColor(mTextColor != null ? mTextColor : ColorStateList.valueOf(Color.BLACK));
        label.setBackgroundDrawable(mLabelBg.getConstantState().newDrawable());
        label.setTag(KEY_DATA, data);
        label.setTag(KEY_POSITION, position);
        label.setOnClickListener(this);
        addView(label);
        if (!TextUtils.isEmpty(str) && str.contains(provider.getLabelText(label, position, data).toString())) {
            setLabelSelect(label, true);
        }
        label.setText(provider.getLabelText(label, position, data));
    }

    /**
     * 确保标签是否能响应事件，如果标签可选或者标签设置了点击事件监听，则响应事件。
     */
    private void ensureLabelClickable() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TextView label = (TextView) getChildAt(i);
            label.setClickable(mLabelClickListener != null || mSelectType != SelectType.NONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            TextView label = (TextView) v;
            if (mSelectType != SelectType.NONE) {
                if (label.isSelected()) {
                    if (mSelectType != SelectType.SINGLE_IRREVOCABLY
                            && !mCompulsorys.contains((Integer) label.getTag(KEY_POSITION))) {
                        setLabelSelect(label, false);
                    }
                } else if (mSelectType == SelectType.SINGLE || mSelectType == SelectType.SINGLE_IRREVOCABLY) {
                    innerClearAllSelect();
                    setLabelSelect(label, true);
                } else if (mSelectType == SelectType.MULTI
                        && (mMaxSelect <= 0 || mMaxSelect > mSelectLabels.size())) {
                    setLabelSelect(label, true);
                }
            }

            if (mLabelClickListener != null) {
                mLabelClickListener.onLabelClick(label, label.getTag(KEY_DATA), (int) label.getTag(KEY_POSITION));
            }

        }
    }

    private void setLabelSelect(TextView label, boolean isSelect) {
        if (label.isSelected() != isSelect) {
            label.setSelected(isSelect);
            if (isSelect) {
                mSelectLabels.add((Integer) label.getTag(KEY_POSITION));
            } else {
                mSelectLabels.remove((Integer) label.getTag(KEY_POSITION));
            }
            if (mLabelSelectChangeListener != null) {
                mLabelSelectChangeListener.onLabelSelectChange(label, label.getTag(KEY_DATA),
                        isSelect, (int) label.getTag(KEY_POSITION));
            }
        }
    }

    /**
     * 取消所有选中的label
     */
    public void clearAllSelect() {
        if (mSelectType != SelectType.SINGLE_IRREVOCABLY) {
            if (mSelectType == SelectType.MULTI && !mCompulsorys.isEmpty()) {
                clearNotCompulsorySelect();
            } else {
                innerClearAllSelect();
            }
        }
    }

    public void innerClearAllSelect() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            setLabelSelect((TextView) getChildAt(i), false);
        }
        mSelectLabels.clear();
    }

    private void clearNotCompulsorySelect() {
        int count = getChildCount();
        List<Integer> temps = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (!mCompulsorys.contains(i)) {
                setLabelSelect((TextView) getChildAt(i), false);
                temps.add(i);
            }

        }
        mSelectLabels.removeAll(temps);
    }

    /**
     * 设置选中label
     *
     * @param positions
     */
    public void setSelects(List<Integer> positions) {
        if (positions != null) {
            int size = positions.size();
            int[] ps = new int[size];
            for (int i = 0; i < size; i++) {
                ps[i] = positions.get(i);
            }
            setSelects(ps);
        }
    }

    /**
     * 设置选中label
     *
     */
    public void setAllSelects() {
        if (getLabels() != null) {
            int size = getLabels().size();
            int[] ps = new int[size];
            for (int i = 0; i < size; i++) {
                ps[i] = i;
            }
            setSelects(ps);
        }
    }

    /**
     * 设置选中label
     *
     * @param positions
     */
    public void setSelects(int... positions) {
        if (mSelectType != SelectType.NONE) {
            ArrayList<TextView> selectLabels = new ArrayList<>();
            int count = getChildCount();
            int size = mSelectType == SelectType.SINGLE || mSelectType == SelectType.SINGLE_IRREVOCABLY
                    ? 1 : mMaxSelect;
            for (int p : positions) {
                if (p < count) {
                    TextView label = (TextView) getChildAt(p);
                    if (!selectLabels.contains(label)) {
                        setLabelSelect(label, true);
                        selectLabels.add(label);
                    }
                    if (size > 0 && selectLabels.size() == size) {
                        break;
                    }
                }
            }

            for (int i = 0; i < count; i++) {
                TextView label = (TextView) getChildAt(i);
                if (!selectLabels.contains(label)) {
                    setLabelSelect(label, false);
                }
            }
        }
    }

    /**
     * 设置必选项，只有在多项模式下，这个方法才有效
     *
     * @param positions
     */
    public void setCompulsorys(List<Integer> positions) {
        if (mSelectType == SelectType.MULTI && positions != null) {
            mCompulsorys.clear();
            mCompulsorys.addAll(positions);
            //必选项发生改变，就要恢复到初始状态。
            innerClearAllSelect();
            setSelects(positions);
        }
    }

    /**
     * 设置必选项，只有在多项模式下，这个方法才有效
     *
     * @param positions
     */
    public void setCompulsorys(int... positions) {
        if (mSelectType == SelectType.MULTI && positions != null) {
            List<Integer> ps = new ArrayList<>(positions.length);
            for (int i : positions) {
                ps.add(i);
            }
            setCompulsorys(ps);
        }
    }

    /**
     * 获取必选项，
     *
     * @return
     */
    public List<Integer> getCompulsorys() {
        return mCompulsorys;
    }

    /**
     * 清空必选项，只有在多项模式下，这个方法才有效
     */
    public void clearCompulsorys() {
        if (mSelectType == SelectType.MULTI && !mCompulsorys.isEmpty()) {
            mCompulsorys.clear();
            innerClearAllSelect();
        }
    }

    /**
     * 获取选中的label(返回的是所有选中的标签的位置)
     *
     * @return
     */
    public List<Integer> getSelectLabels() {
        return mSelectLabels;
    }

    /**
     * 获取选中的label(返回的是所头选中的标签的数据)
     *
     * @param <T>
     * @return
     */
    public <T> List<T> getSelectLabelDatas() {
        List<T> list = new ArrayList<>();
        int size = mSelectLabels.size();
        for (int i = 0; i < size; i++) {
            View label = getChildAt(mSelectLabels.get(i));
            Object data = label.getTag(KEY_DATA);
            if (data != null) {
                list.add((T) data);
            }
        }
        return list;
    }

    /**
     * 设置标签背景
     *
     * @param resId
     */
    public void setLabelBackgroundResource(int resId) {
        setLabelBackgroundDrawable(getResources().getDrawable(resId));
    }

    /**
     * 设置标签背景
     *
     * @param color
     */
    public void setLabelBackgroundColor(int color) {
        setLabelBackgroundDrawable(new ColorDrawable(color));
    }

    /**
     * 设置标签背景
     *
     * @param drawable
     */
    public void setLabelBackgroundDrawable(Drawable drawable) {
        mLabelBg = drawable;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TextView label = (TextView) getChildAt(i);
            label.setBackgroundDrawable(mLabelBg.getConstantState().newDrawable());
        }
    }

    /**
     * 设置标签内边距
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setLabelTextPadding(int left, int top, int right, int bottom) {
        if (mTextPaddingLeft != left || mTextPaddingTop != top
                || mTextPaddingRight != right || mTextPaddingBottom != bottom) {
            mTextPaddingLeft = left;
            mTextPaddingTop = top;
            mTextPaddingRight = right;
            mTextPaddingBottom = bottom;
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                TextView label = (TextView) getChildAt(i);
                label.setPadding(left, top, right, bottom);
            }
        }
    }

    public int getTextPaddingLeft() {
        return mTextPaddingLeft;
    }

    public int getTextPaddingTop() {
        return mTextPaddingTop;
    }

    public int getTextPaddingRight() {
        return mTextPaddingRight;
    }

    public int getTextPaddingBottom() {
        return mTextPaddingBottom;
    }

    /**
     * 设置标签的文字大小（单位是px）
     *
     * @param size
     */
    public void setLabelTextSize(int size) {
        if (mTextSize != size) {
            mTextSize = size;
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                TextView label = (TextView) getChildAt(i);
                label.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            }
        }
    }

    public float getLabelTextSize() {
        return mTextSize;
    }

    /**
     * 设置标签的文字颜色
     *
     * @param color
     */
    public void setLabelTextColor(int color) {
        setLabelTextColor(ColorStateList.valueOf(color));
    }

    /**
     * 设置标签的文字颜色
     *
     * @param color
     */
    public void setLabelTextColor(ColorStateList color) {
        mTextColor = color;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TextView label = (TextView) getChildAt(i);
            label.setTextColor(mTextColor != null ? mTextColor : ColorStateList.valueOf(0xFF000000));
        }
    }

    public ColorStateList getLabelTextColor() {
        return mTextColor;
    }

    /**
     * 设置行间隔
     */
    public void setLineMargin(int margin) {
        if (mLineMargin != margin) {
            mLineMargin = margin;
            requestLayout();
        }
    }

    public int getLineMargin() {
        return mLineMargin;
    }

    /**
     * 设置标签的间隔
     */
    public void setWordMargin(int margin) {
        if (mWordMargin != margin) {
            mWordMargin = margin;
            requestLayout();
        }
    }

    public int getWordMargin() {
        return mWordMargin;
    }

    /**
     * 设置标签的选择类型
     *
     * @param selectType
     */
    public void setSelectType(SelectType selectType) {
        if (mSelectType != selectType) {
            mSelectType = selectType;
            //选择类型发生改变，就要恢复到初始状态。
            innerClearAllSelect();

            if (mSelectType == SelectType.SINGLE_IRREVOCABLY) {
                setSelects(0);
            }

            if (mSelectType != SelectType.MULTI) {
                mCompulsorys.clear();
            }

            ensureLabelClickable();
        }
    }

    public SelectType getSelectType() {
        return mSelectType;
    }

    /**
     * 设置最大的选择数量
     *
     * @param maxSelect
     */
    public void setMaxSelect(int maxSelect) {
        if (mMaxSelect != maxSelect) {
            mMaxSelect = maxSelect;
            if (mSelectType == SelectType.MULTI) {
                innerClearAllSelect();
            }
        }
    }

    public int getMaxSelect() {
        return mMaxSelect;
    }

    /**
     * 设置最大行数，小于等于0则不限行数。
     *
     * @param maxLines
     */
    public void setMaxLines(int maxLines) {
        if (mMaxLines != maxLines) {
            mMaxLines = maxLines;
            requestLayout();
        }
    }

    public int getMaxLines() {
        return mMaxLines;
    }

    /**
     * 设置标签的点击监听
     *
     * @param l
     */
    public void setOnLabelClickListener(OnLabelClickListener l) {
        mLabelClickListener = l;
        ensureLabelClickable();
    }

    /**
     * 设置标签的选择监听
     *
     * @param l
     */
    public void setOnLabelSelectChangeListener(OnLabelSelectChangeListener l) {
        mLabelSelectChangeListener = l;
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public interface OnLabelClickListener {

        /**
         * @param label    标签
         * @param data     标签对应的数据
         * @param position 标签位置
         */
        void onLabelClick(TextView label, Object data, int position);
    }

    public interface OnLabelSelectChangeListener {

        /**
         * @param label    标签
         * @param data     标签对应的数据
         * @param isSelect 是否选中
         * @param position 标签位置
         */
        void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position);
    }

    /**
     * 给标签提供最终需要显示的数据。因为LabelsView的列表可以设置任何类型的数据，而LabelsView里的每个item的是一
     * 个TextView，只能显示CharSequence的数据，所以LabelTextProvider需要根据每个item的数据返回item最终要显示
     * 的CharSequence。
     *
     * @param <T>
     */
    public interface LabelTextProvider<T> {

        /**
         * 根据data和position返回label需要需要显示的数据。
         *
         * @param label
         * @param position
         * @param data
         * @return
         */
        CharSequence getLabelText(TextView label, int position, T data);
    }

    private Drawable createBackgroundDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        if (mSelectedBackground != 0) {
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, getBackgroundDrawable(mSelectedBackground, mSelectedStrokeColor));
        }
        stateListDrawable.addState(new int[]{}, getBackgroundDrawable(mBackground == 0 ? Color.WHITE : mBackground, mStrokeColor));
        return stateListDrawable;
    }

    private Drawable getBackgroundDrawable(int backgroundColor, int strokeColor) {
        GradientDrawable dw = new GradientDrawable();
        dw.setColor(backgroundColor);
        if (mStrokeWidth != 0) {
            dw.setStroke(mStrokeWidth, strokeColor == 0 ? Color.TRANSPARENT : strokeColor);
        }
        if (mRoundRadius != 0) {
            dw.setCornerRadius(mRoundRadius);
        }
        return dw;
    }

    /**
     * 对TextView设置、selected状态时其文字颜色。
     */
    private ColorStateList createColorStateList(int selectedTextColor, int normalTextColor) {
        if (selectedTextColor == 0 || normalTextColor == 0) {
            int defaultTextColor = 0;
            if (selectedTextColor != 0) {
                defaultTextColor = selectedTextColor;
            }
            if (normalTextColor != 0) {
                defaultTextColor = normalTextColor;
            }
            int[] colors = new int[]{defaultTextColor};
            int[][] states = new int[1][];
            states[0] = new int[]{};
            return new ColorStateList(states, colors);
        }
        int[] colors = new int[]{selectedTextColor, normalTextColor};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
}
