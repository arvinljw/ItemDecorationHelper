package net.arvin.itemdecorationhelper.sample;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by arvinljw on 2018/7/25 11:15
 * Function：
 * Desc：
 */
public class TestData {
    public static List<ContactEntity> getData() {
        List<String> names = new ArrayList<>(Arrays.asList("李渊", "李世民", "侯君集", "李靖", "魏征", "房玄龄", "杜如晦", "柴绍", "程知节", "尉迟恭", "秦琼",
                "长孙无忌", "李存恭", "封德彝", "段志玄", "刘弘基", "徐世绩", "李治", "武则天", "太平公主", "韦后", "李隆基", "杨玉环", "王勃",
                "陈子昂", "卢照邻", "杨炯", "王之涣", "安禄山", "史思明", "张巡", "雷万春", "李白", "高力士", "杜甫", "白居易", "王维", "孟浩然",
                "杜牧", "李商隐", "郭子仪", "张易之", "张昌宗", "来俊臣", "杨国忠", "李林甫", "高适", "王昌龄", "孙思邈", "玄奘", "鉴真", "高骈",
                "狄仁杰", "黄巢", "王仙芝", "文成公主", "松赞干布", "薛涛", "鱼玄机", "贺知章", "李泌", "韩愈", "柳宗元", "上官婉儿", "朱温",
                "刘仁恭", "丁会", "李克用", "李存勖", "葛从周", "王建", "刘知远", "石敬瑭", "郭威", "柴荣", "孟昶", "荆浩", "刘彟", "张及之", "杜宇",
                "高季兴", "喻皓", "历真", "李茂贞", "朱友珪", "朱友贞", "刘守光", "卢文进", "李嗣源", "冯行袭", "康义诚", "薛贻矩", "朱弘昭", "冯赟",
                "李存孝", "霍存", "张归霸", "张延寿", "氏叔琮", "朱瑾", "朱珍", "张存敬", "牛存节", "李罕之", "乐从训", "王师范", "康怀英", "王彦章",
                "时溥", "秦宗权", "史懿", "苏逢吉", "杨邡", "桑维汉", "耶律德光", "安重荣", "边光范", "袁继忠", "李筠", "薛怀让", "赵匡胤", "赵匡义",
                "石守信", "慕容延钊", "曹彬", "潘美", "赵普", "杨业", "田重进", "王禹偁", "林逋", "杨延昭", "杨文广", "包拯", "狄青", "寇准", "范仲淹",
                "司马光", "欧阳修", "苏轼", "苏辙", "王安石", "吕惠卿", "苏洵", "宋江", "方腊", "岳飞", "秦桧", "韩世忠", "梁红玉",
                "赵构", "朱熹", "柳永", "黄庭坚", "秦观", "晏殊", "晏几道", "陆游", "辛弃疾", "魏良臣", "李清照", "唐婉", "史弥远", "韩佗胄",
                "贾似道", "丁大全", "文天祥", "陆秀夫", "高俅", "蔡京", "杨戬", "童贯", "张叔夜", "韩锜", "岳云", "张宪", "梅尧臣", "苏舜钦",
                "吕文焕", "吕文德", "杨幺"));
        List<ContactEntity> contacts = new ArrayList<>();
        for (String name : names) {
            ContactEntity entity = new ContactEntity(name);
            entity.setPinyinName(HanziToPinyin.getPinYin(name));
            entity.setLetter(getLetter(entity.getPinyinName()));
            contacts.add(entity);
        }
        Collections.sort(contacts, new NameComparator());
        return contacts;
    }

    private static String getLetter(String pinyinName) {
        char ch = pinyinName.charAt(0);
        if (ch < 'a' || ch > 'z') {
            ch = '#';
        }
        return String.valueOf(ch).toUpperCase();
    }

    public static class NameComparator implements Comparator<ContactEntity> {

        @Override
        public int compare(ContactEntity o1, ContactEntity o2) {
            Comparator<Object> cmp = Collator.getInstance(Locale.CHINA);
            String[] str = new String[2];
            str[0] = o1.getName();
            str[1] = o2.getName();
            Arrays.sort(str, cmp);
            if (str[0].equals(str[1])) {
                return 0;
            }
            if (str[0].equals(o1.getName())) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
