package com.daking.lottery.widget;


import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;

/**
 * https://github.com/adonis-lsh/BankCardNumFormat
 */
public class BankNumEditText extends AppCompatEditText {

    public BankNumEditText(Context context) {
        this(context, null);
    }

    public BankNumEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BankNumEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);
        setEnabled(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void setInputType(int type) {
        super.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        //如果长度大于4位的话,我才去格式化
        String inputString = text.toString();
        if (inputString.length() > 4) {
            //最多只能输入19位
            if (inputString.length() < 19 + 4) {
                boolean isMatch = inputString.matches("^(\\d{4,}\\s){1,5}(\\d{1,4})$");
                //如果符合规定的正则表达式的话,就不去格式化文本
                if (!isMatch) {
                    formatCardNum(text);
                }
            } else {
                //超过之后就不能输入
                setFocusable(true);
            }
        }

    }

    private void formatCardNum(CharSequence text) {
        //先去掉所有的空格,因为有可能用户在输入的过程中有空格
        String originCardNum = text.toString().trim().replace(" ", "");
        int len = originCardNum.length();
        int courPos;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(originCardNum.charAt(i));
            if (i == 3 || i == 7 || i == 11 || i == 15 || i == 19) {
                //判断是不是最后一位,最后一位不加空格" "
                if (i != len - 1)
                    builder.append(" ");
            }
        }
        courPos = builder.length();
        setText(builder.toString());
        setSelection(courPos);
    }

    public String getBankNum() {
        return getText().toString().trim().replaceAll(" ", "");
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhn 校验算法获得校验位
     * 1、从卡号最后一位数字开始,偶数位乘以2,如果乘以2的结果是两位数，将结果减去9。
     * 2、把所有数字相加,得到总和。
     * 3、如果信用卡号码是合法的，总和可以被10整除。
     * 网上有很多算法都是错的,请认真看算法
     *
     * @param cardId 银行卡号
     * @return 返回真, 说明是银行卡号
     */
    public boolean isBankCard(String cardId) {
        int sum = 0;
        char[] chs = cardId.toCharArray();
        int[] intChs = new int[chs.length];
        for (int i = 0; i < chs.length; i++) {
            intChs[i] = Integer.parseInt(String.valueOf(chs[i]));
        }
        for (int position = 1, j = intChs.length - 1; position <= intChs.length; position++, j--) {
            if (position % 2 == 0) {
                if ((intChs[j] * 2) > 9) {
                    sum += ((intChs[j] * 2) - 9);
                } else {
                    sum += intChs[j] * 2;
                }
            } else if (position % 2 == 1) {
                sum += intChs[j];
            }
        }

        return sum % 10 == 0;
    }
}
