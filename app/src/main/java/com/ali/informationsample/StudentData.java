package com.ali.informationsample;

import java.util.List;

public class StudentData {

    /**
     * classinfo : {"classlogo":"http://blog.zhaoliang5156.cn/api/images/classlogo.png","classtitle":"移动通信学院青春30K高薪班座次表"}
     * students : [{"icon":"","name":"大神1"},{"icon":"","name":"大神2"},{"icon":"","name":"大神3"},{"icon":"","name":"大神4"},{"icon":"","name":"大神5"},{"icon":"","name":"大神6"},{"icon":"","name":"大神7"},{"icon":"","name":"大神8"},{"icon":"","name":"大神9"},{"icon":"","name":"大神10"},{"icon":"","name":"大神11"},{"icon":"","name":"大神12"},{"icon":"","name":"大神13"},{"icon":"","name":"大神14"},{"icon":"","name":"大神15"}]
     */

    private ClassinfoBean classinfo;
    private List<StudentsBean> students;

    public ClassinfoBean getClassinfo() {
        return classinfo;
    }

    public void setClassinfo(ClassinfoBean classinfo) {
        this.classinfo = classinfo;
    }

    public List<StudentsBean> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsBean> students) {
        this.students = students;
    }

    public static class ClassinfoBean {
        /**
         * classlogo : http://blog.zhaoliang5156.cn/api/images/classlogo.png
         * classtitle : 移动通信学院青春30K高薪班座次表
         */

        private String classlogo;
        private String classtitle;

        public String getClasslogo() {
            return classlogo;
        }

        public void setClasslogo(String classlogo) {
            this.classlogo = classlogo;
        }

        public String getClasstitle() {
            return classtitle;
        }

        public void setClasstitle(String classtitle) {
            this.classtitle = classtitle;
        }
    }

    public static class StudentsBean {
        /**
         * icon :
         * name : 大神1
         */

        private String icon;
        private String name;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
