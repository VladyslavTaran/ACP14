package db;

import java.util.List;

/**
 * Created by Vlad on 23.10.2016.
 */
public interface IGetAllByGroupId<T> {
    List<T > getAllByGroupId(int groupId);
}