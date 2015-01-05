package com.example.saf.domain;

import cn.salesuite.saf.orm.DBDomain;
import cn.salesuite.saf.orm.annotation.Column;
import cn.salesuite.saf.orm.annotation.Table;

/**
 * 继承DBDomain默认有id字段，所有不需要再写id，写了就重复，不能建表
 * @author shwuyiqun
 *
 */
@Table(name = "contributor")
public class Contributor extends DBDomain {

//	@Column(name = "id", length = 20)
//	public long id;// 66577,
	@Column(name = "contributions", length = 20)
	public long contributions;// 461
	@Column(name = "gravatar_id", length = 20)
	public long gravatar_id;//
	@Column(name = "site_admin", length = 20)
	public boolean site_admin;// false,
	@Column(name = "login", length = 100)
	public String login;//
	@Column(name = "avatar_url", length = 100)
	public String avatar_url;//
	@Column(name = "url", length = 100)
	public String url;//
	@Column(name = "html_url", length = 100)
	public String html_url;//
	@Column(name = "followers_url", length = 100)
	public String followers_url;//
	@Column(name = "following_url", length = 100)
	public String following_url;//
	@Column(name = "gists_url", length = 100)
	public String gists_url;//
	@Column(name = "starred_url", length = 100)
	public String starred_url;//
	@Column(name = "subscriptions_url", length = 100)
	public String subscriptions_url;//
	@Column(name = "organizations_url", length = 100)
	public String organizations_url;//
	@Column(name = "repos_url", length = 100)
	public String repos_url;//
	@Column(name = "events_url", length = 100)
	public String events_url;//
	@Column(name = "received_events_url", length = 100)
	public String received_events_url;//
	@Column(name = "type", length = 100)
	public String type;//
	
	@Override
	public String toString() {
		return login + "," + type;
	}
}
