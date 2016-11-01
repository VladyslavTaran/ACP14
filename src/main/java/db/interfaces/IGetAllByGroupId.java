package db.interfaces;

import java.util.List;

public interface IGetAllByGroupId<T> {
    List<T > getAllByGroupId(int groupId);
}