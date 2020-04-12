package com.site.steel.core.dao;

import com.site.steel.core.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Component
public interface UserMapper {

    Logger logger = LoggerFactory.getLogger(UserMapper.class);

    @Insert("insert into tb_user (user_id, login_user_name, login_password, " +
            "      nick_name, phone, locked)" +
            "    values (#{adminUserId,jdbcType=INTEGER}, #{loginUserName,jdbcType=VARCHAR}, #{loginPassword,jdbcType=VARCHAR}, " +
            "      #{nickName,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, #{locked,jdbcType=TINYINT})")
    int insert(User record);

    @InsertProvider(type = UserSqlBuilder.class, method = "insertSelective")
    int insertSelective(User record);

    /**
     * 登陆方法
     *
     * @param userName
     * @param password
     * @return
     */
    @Select("select user_id, login_user_name, login_password, login_plaintext_password, nick_name, phone, locked from tb_user where login_user_name = #{userName} AND login_password = #{password} AND locked = 0")
    User login(@Param("userName") String userName, @Param("password") String password);

    @Select("select * from tb_user where user_id = #{adminUserId}")
    User selectByPrimaryKey(Integer adminUserId);

    @Select("select * from tb_user ")
    List<User> findAll();

    @UpdateProvider(type = UserSqlBuilder.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User user);

    @Update(" update tb_user" +
            "    set login_user_name = #{loginUserName,jdbcType=VARCHAR}," +
            "      login_password = #{loginPassword,jdbcType=VARCHAR}," +
            "      login_plaintext_password = #{plaintextPassword,jdbcType=VARCHAR}," +
            "      nick_name = #{nickName,jdbcType=VARCHAR}," +
            "      phone, = #{phone,jdbcType=VARCHAR}," +
            "      locked = #{locked,jdbcType=TINYINT}" +
            "    where user_id = #{adminUserId,jdbcType=INTEGER}")
    int updateByPrimaryKey(User record);

    class UserSqlBuilder {
        public String insertSelective(User user) {
            StringBuilder sql = new StringBuilder();
            StringBuilder sqlValues = new StringBuilder(" values (");
            sql.append("insert into tb_user (");
            if (user.getUserId() != null) {
                sql.append("user_id, ");
                sqlValues.append("#{adminUserId,jdbcType=INTEGER},");
            }
            if (user.getLoginUserName() != null) {
                sql.append("login_user_name, ");
                sqlValues.append("#{loginUserName,jdbcType=VARCHAR},");
            }
            if (user.getLoginPassword() != null) {
                sql.append("login_password, ");
                sqlValues.append("#{loginPassword,jdbcType=VARCHAR},");
            }
            if (user.getPlaintextPassword() != null) {
                sql.append("login_plaintext_password, ");
                sqlValues.append("#{plaintextPassword,jdbcType=VARCHAR},");
            }
            if (user.getNickName() != null) {
                sql.append("nick_name, ");
                sqlValues.append("#{nickName,jdbcType=VARCHAR},");
            }
            if (user.getPhone() != null) {
                sql.append("phone, ");
                sqlValues.append("#{phone,jdbcType=VARCHAR},");
            }
            if (user.getLocked() != null) {
                sql.append("locked) ");
                sqlValues.append("#{locked,jdbcType=TINYINT} )");
            }
            sql.append(sqlValues);
            logger.info("sql语句=== {}", sql.toString());
            return sql.toString();
        }

        public String updateByPrimaryKeySelective(User user) {
            StringBuilder sql = new StringBuilder();
            sql.append("update blog_config set ");
            if (StringUtil.isNotEmpty(user.getLoginUserName())) {
                sql.append(" login_user_name = #{loginUserName,jdbcType=VARCHAR}, ");
            }
            if (StringUtil.isNotEmpty(user.getLoginPassword())) {
                sql.append(" login_password = #{loginPassword,jdbcType=VARCHAR}, ");
            }
            if (StringUtil.isNotEmpty(user.getPlaintextPassword())) {
                sql.append(" login_plaintext_password = #{plaintextPassword,jdbcType=VARCHAR}, ");
            }
            if (StringUtil.isNotEmpty(user.getNickName())) {
                sql.append(" nick_name = #{nickName,jdbcType=VARCHAR}, ");
            }
            if (StringUtil.isNotEmpty(user.getPhone())) {
                sql.append(" phone = #{phone,jdbcType=VARCHAR}, ");
            }
            if (user.getLocked() != null) {
                sql.append(" locked = #{locked,jdbcType=TINYINT}");
            }
            sql.append("  where user_id = #{adminUserId,jdbcType=INTEGER}");
            return sql.toString();
        }
    }

}