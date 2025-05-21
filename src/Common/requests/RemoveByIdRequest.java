package Common.requests;

import java.util.Objects;

public class RemoveByIdRequest extends Request {
    private final Long id;

    public RemoveByIdRequest(Long id, String login, String password) {
        super("remove_by_id", login, password);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RemoveByIdRequest)) return false;
        if (!super.equals(o)) return false;
        RemoveByIdRequest that = (RemoveByIdRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}