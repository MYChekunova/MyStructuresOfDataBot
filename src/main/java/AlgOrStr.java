public class AlgOrStr {
    protected Integer id;
    protected String name;
    protected boolean isAlg;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAlg() {
        return isAlg;
    }

    public AlgOrStr(){}
    public AlgOrStr(int id, String name, boolean isAlg){
        this.id = id;
        this.name = name;
        this.isAlg = isAlg;
    }
    @Override
    public String toString() {
        if (isAlg){
            return id+" "+name+" - алгоритм";
        } else{
            return id+" "+name+" - структура данных";
        }
    }
}
