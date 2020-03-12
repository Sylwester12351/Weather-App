package model;

public class Model {
    private int id;
    private String datatime;
    private int temp;

    public Model(Builder b) {
        this.id = b.id;
        this.datatime = b.datatime;
        this.temp = b.temp;
    }

    public static class Builder {
        private int id;
        private String datatime;
        private int temp;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder datatime(String datatime) {
            this.datatime = datatime;
            return this;
        }

        public Builder temp(int temp) {
            this.temp = temp;
            return this;
        }

        public Model build() {
            return new Model(this);
        }
    } //end builder

    public int getId() {
        return id;
    }

    public String getdatatime() {
        return datatime;
    }

    public int getTemp() {
        return temp;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", datatime='" + datatime + '\'' +
                ", temp=" + temp +
                '}';
    }

}
