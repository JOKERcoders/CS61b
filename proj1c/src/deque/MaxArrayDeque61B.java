package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque61B(Comparator<T> c) {
        super();              // 调用父类构造器
        this.comparator = c;  // 保存默认比较器
    }

    public T max() {
        return max(this.comparator);
    }

    public T max(Comparator<T> c) {
        if (this.size() == 0) {
            return null;
        }

        T maxItem = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            T current = this.get(i);
            if (c.compare(current, maxItem) > 0) {
                maxItem = current;
            }
        }
        return maxItem;
    }
}
