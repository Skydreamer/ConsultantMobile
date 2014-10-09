package items;

public class Consultant {
	public static long amount;
	
	private long publicId;
	private String name;
	private String website;
	private String info;
	
	public Consultant(long id, String name)
	{
		this.publicId = id;
		this.name = name;
		
		amount++;
	}
	
	public Consultant(long id, String name, String website, String info)
	{
		this.publicId = id;
		this.name = name;
		this.website = website;
		this.info = info;
		
		amount++;
	}

	public long getPublicId() {
		return publicId;
	}

	public void setPublicId(long publicId) {
		this.publicId = publicId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
