package com.shoppingService.Test;

public class Test10 {
	public static void main(String args[]) {
		String a = "54564DSFSDF65,524618GHY,456321hGFH,FGTD125478";
		System.out.println(a.replace(",", ", "));
		String attachmentUrl="/api/attachments/4941";
		if (attachmentUrl != null && attachmentUrl.startsWith("/api/attachments/")) {
			try {
				String idStr = attachmentUrl.substring(attachmentUrl.lastIndexOf("/") + 1);
				System.out.println(idStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Long extractAttachmentIdFromUrl(String attachmentUrl) {
		attachmentUrl="/api/attachments/4941";
		if (attachmentUrl != null && attachmentUrl.startsWith("/api/attachments/")) {
			try {
				String idStr = attachmentUrl.substring(attachmentUrl.lastIndexOf("/") + 1);
				return Long.valueOf(idStr);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
}
