package Common.requests;

import java.util.Objects;

import Common.models.StudyGroup;


public class UpdateIdRequest extends Request {
    private final Long id;
    private final StudyGroup updatedGroup;

    public UpdateIdRequest(Long id, StudyGroup updatedGroup, String login, String password) {
        super("update_id", login, password);
        this.id = id;
        this.updatedGroup = updatedGroup;
    }

    public Long getId() {
        return id;
    }

    public StudyGroup getUpdatedGroup() {
        return updatedGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateIdRequest)) return false;
        if (!super.equals(o)) return false;
        UpdateIdRequest that = (UpdateIdRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(updatedGroup, that.updatedGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, updatedGroup);
    }
}
