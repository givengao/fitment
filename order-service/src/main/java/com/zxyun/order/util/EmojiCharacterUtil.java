package com.zxyun.order.util;

/**
 * @des:
 * @Author: given
 * @Date 2019/12/25 11:55
 */
public class EmojiCharacterUtil {
    private static final char unicode_separator = '&';
    private static final char unicode_prefix = 'u';
    private static final char separator = ':';

    public EmojiCharacterUtil() {
    }

    private static boolean isEmojiCharacter(int codePoint) {
        return codePoint >= 9728 && codePoint <= 10175 || codePoint == 12349 || codePoint == 8265 || codePoint == 8252 || codePoint >= 8192 && codePoint <= 8207 || codePoint >= 8232 && codePoint <= 8239 || codePoint == 8287 || codePoint >= 8293 && codePoint <= 8303 || codePoint >= 8448 && codePoint <= 8527 || codePoint >= 8960 && codePoint <= 9215 || codePoint >= 11008 && codePoint <= 11263 || codePoint >= 10496 && codePoint <= 10623 || codePoint >= 12800 && codePoint <= 13055 || codePoint >= 55296 && codePoint <= 57343 || codePoint >= 57344 && codePoint <= 63743 || codePoint >= 65024 && codePoint <= 65039 || codePoint >= 65536;
    }

    public static String escape(String src) {
        if (src != null && src.trim().length() != 0) {
            int cpCount = src.codePointCount(0, src.length());
            int firCodeIndex = src.offsetByCodePoints(0, 0);
            int lstCodeIndex = src.offsetByCodePoints(0, cpCount - 1);
            StringBuilder sb = new StringBuilder(src.length());

            for(int index = firCodeIndex; index <= lstCodeIndex; ++index) {
                int codepoint = src.codePointAt(index);
                if (isEmojiCharacter(codepoint)) {
                    String hash = Integer.toHexString(codepoint);
                    sb.append('&').append(hash.length()).append('u').append(':').append(hash);
                    index += (hash.length() - 1) / 4;
                } else {
                    sb.append((char)codepoint);
                }
            }

            return sb.toString();
        } else {
            return src;
        }
    }

    public static String reverse(String src) {
        if (src != null && src.trim().length() != 0) {
            StringBuilder sb = new StringBuilder(src.length());
            char[] sourceChar = src.toCharArray();
            int index = 0;

            while(true) {
                while(true) {
                    while(index < sourceChar.length) {
                        if (sourceChar[index] == '&') {
                            if (index + 6 >= sourceChar.length) {
                                sb.append(sourceChar[index]);
                                ++index;
                            } else if (sourceChar[index + 1] >= '4' && sourceChar[index + 1] <= '6' && sourceChar[index + 2] == 'u' && sourceChar[index + 3] == ':') {
                                int length = Integer.parseInt(String.valueOf(sourceChar[index + 1]));
                                char[] hexchars = new char[length];

                                for(int j = 0; j < length; ++j) {
                                    char ch = sourceChar[index + 4 + j];
                                    if ((ch < '0' || ch > '9') && (ch < 'a' || ch > 'f')) {
                                        sb.append(sourceChar[index]);
                                        ++index;
                                        break;
                                    }

                                    hexchars[j] = ch;
                                }

                                sb.append(Character.toChars(Integer.parseInt(new String(hexchars), 16)));
                                index += 4 + length;
                            } else if (sourceChar[index + 1] != 'u') {
                                sb.append(sourceChar[index]);
                                ++index;
                            } else {
                                char[] hexchars = new char[4];

                                for(int j = 0; j < 4; ++j) {
                                    char ch = sourceChar[index + 2 + j];
                                    if ((ch < '0' || ch > '9') && (ch < 'a' || ch > 'f')) {
                                        sb.append(sourceChar[index]);
                                        ++index;
                                        break;
                                    }

                                    hexchars[j] = ch;
                                    sb.append(Character.toChars(Integer.parseInt(String.valueOf(hexchars), 16)));
                                    index += 6;
                                }
                            }
                        } else {
                            sb.append(sourceChar[index]);
                            ++index;
                        }
                    }

                    return sb.toString();
                }
            }
        } else {
            return src;
        }
    }

    public static String filter(String src) {
        if (src != null && !src.trim().equals("")) {
            int cpCount = src.codePointCount(0, src.length());
            int firCodeIndex = src.offsetByCodePoints(0, 0);
            int lstCodeIndex = src.offsetByCodePoints(0, cpCount - 1);
            StringBuilder sb = new StringBuilder(src.length());

            int codepoint;
            for(int index = firCodeIndex; index <= lstCodeIndex; index += Character.isSupplementaryCodePoint(codepoint) ? 2 : 1) {
                codepoint = src.codePointAt(index);
                if (!isEmojiCharacter(codepoint)) {
                    System.err.println("codepoint:" + Integer.toHexString(codepoint));
                    sb.append((char)codepoint);
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(filter(" AB C"));
        System.out.println(filter("测试 测试"));
        System.out.println(escape("A\ud83d\ude10昭"));
        System.out.println(reverse("A&5u:1f610昭"));
    }
}
