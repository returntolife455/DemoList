package com.returntolife.jjcode.mydemolist.demo.function.section;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.Dp2PxUtil;


/**
 * Created by HeJiaJun on 2019/8/19.
 * Email:455hejiajun@gmail
 * des:
 */
public class PinnedSectionDecoration extends RecyclerView.ItemDecoration {

    private DecorationCallback callback;
    private TextPaint textPaint;
    private Paint paint;
    private int topGap;

    private int textPaddingLeft;
    private int textSize;

    public PinnedSectionDecoration(Context context, DecorationCallback decorationCallback) {
        Resources res = context.getResources();
        this.callback = decorationCallback;

        paint = new Paint();
        paint.setColor(res.getColor(R.color.white));

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);

        textSize= Dp2PxUtil.dip2px(context,14);
        textPaint.setTextSize(textSize);
        textPaint.setColor(ContextCompat.getColor(context,R.color.colorPrimary));
        textPaint.getFontMetrics(new Paint.FontMetrics());
        textPaint.setTextAlign(Paint.Align.LEFT);

        topGap = Dp2PxUtil.dip2px(context,60);
        textPaddingLeft= Dp2PxUtil.dip2px(context,16);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int preGroupId, groupId = 0;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = callback.getGroupId(position);
            if (groupId == preGroupId){
                continue;
            }

            String textLine = callback.getGroupFirstLine(position);
            if (TextUtils.isEmpty(textLine)){
                continue;
            }

            int viewBottom = view.getBottom();
            int viewTop=view.getTop();
            float textY = Math.max(topGap, viewTop);
            if (position + 1 < itemCount) { //下一个和当前不一样移动当前
                int nextGroupId = callback.getGroupId(position + 1);
                if (nextGroupId != groupId && viewBottom < textY ) {//组内最后一个view进入了header
                    textY = viewBottom;
                }
            }

            c.drawRect(left, textY-topGap, right, textY, paint);
            //绘制在top高度的中间位置
            c.drawText(textLine, textPaddingLeft, textY-(topGap-textSize)/2, textPaint);
        }
    }

    private boolean isFirstInGroup(int pos) {
        long prevGroupId = callback.getGroupId(pos - 1);
        long groupId = callback.getGroupId(pos);
        return prevGroupId != groupId;
    }

    public interface DecorationCallback {

        int getGroupId(int position);

        String getGroupFirstLine(int position);
    }
}
