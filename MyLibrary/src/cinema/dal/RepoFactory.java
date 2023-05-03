package cinema.dal;

/**
 *
 * @author karlo_6zwakzv
 */
import cinema.dalSql.SQLRepo;

public class RepoFactory
{
    public RepoFactory() {

    }

    public static Repo GetRepository(){

        return new SQLRepo();
    }
}
