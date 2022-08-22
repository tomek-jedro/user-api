package db

import com.github.tminglei.slickpg.ExPostgresProfile

trait CustomPostgresProfile extends ExPostgresProfile {
  override protected def computeCapabilities: Set[slick.basic.Capability] =
    super.computeCapabilities + slick.jdbc.JdbcCapabilities.insertOrUpdate

  override val api: MyAPI.type = MyAPI

  object MyAPI extends API
}

object CustomPostgresProfile extends CustomPostgresProfile
