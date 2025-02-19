package utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TrackingParams {
	
	public static String[] KNOWN_PARAMS = {
		// Google Analytics & Ads
		"utm_source",
		"utm_medium",
		"utm_campaign",
		"utm_content",
		"utm_source_platform",
		"utm_id",
		"utm_term",
		"gclid",
		"wbraid",
		"gbraid",
		"dclid",
		"gad_source",
		"gad",
		"gclsrc",
		"ghl",
		
		// Facebook
		"fbclid",
		"fb_action_ids",
		"fb_action_types",
		"fb_source",
		"fb_ref",
		"fbc",
		"fbp",
		
		// Microsoft
		"msclkid",
		"mkt_tok",
		
		// Email Marketing Systems
		// Mailchimp
		"mc_cid",
		"mc_eid",
		// Hubspot
		"_hsenc",
		"_hsmi",
		"hs_message_id",
		// Emma
		"em_cid",
		"cc_id",
		// Vero
		"vero_conf",
		"vero_id",
		
		// Social Media & Ads
		"ttclid",           // TikTok
		"ob_click_id",      // Outbrain
		"icid",             // Internal campaign ID
		"trk",              // Generic tracking
		"ref",              // Generic referral
		
		// Analytics & Attribution
		"_ga",
		"_gl",
		"ir_campaignid",
		"ir_adid",
		"_kx",
		"__io",
		"__ptq",
		"__hstc",
		"__hssc",
		"__hsfp",
		"_ke",
		"_ke_id",
	};
	
	public static Set<String> KNOWN_PARAMS_SET = new HashSet<>(Arrays.asList(TrackingParams.KNOWN_PARAMS));
}
