try{
ConfigurationBuilder cb = new ConfigurationBuilder();
cb.setDebugEnabled(true)
  .setOAuthConsumerKey("amssqZdWo4YasEHpYacSnvLBL")
  .setOAuthConsumerSecret("p1RjOH9z75rVXBDlPD4pfX2KV11S7glrZycxDptRgO5thDINkb")
  .setOAuthAccessToken("4882733018-6r7fqkuAiWOtXGorBCX0eXbt3Kn6QezyW54H00d")
  .setOAuthAccessTokenSecret("oTPnpb2asAsos7N4OWGZUeURrlPXBmn2TLcG3hk0o68Dg");

TwitterFactory tf = new TwitterFactory(cb.build());

Twitter tw = tf.getInstance();

defaultPose = new CRobotPose();
		defaultPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{0,-900,0,900,0,0,0,0}
						);
		defaultPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{100,100,100,100,100,100,100,100}
						);
		defaultPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
						new Short[]{0,-255,0,180,80,0,180,80,0}
						);
	leftUpPose = new CRobotPose();
		leftUpPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{0,500,0,900,0,0,0,0}
						);
		leftUpPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{100,100,100,100,100,100,100,100}
						);
		leftUpPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
						new Short[]{0,-255,0,180,80,0,180,80,0}
						);
	rightUpPose = new CRobotPose();
		rightUpPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{0,-900,0,-500,0,0,0,0}
						);
		rightUpPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{100,100,100,100,100,100,100,100}
						);
		rightUpPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
						new Short[]{0,-255,0,180,80,0,180,80,0}
						);
	beShyPose = new CRobotPose();					
		beShyPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{-15,-14,-900,14,900,2,50,-2}
						);
		beShyPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{100,100,100,100,100,100,100,100}
						);
		beShyPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
						new Short[]{0,-255,0,140,30,30,140,30,30}
						);
						
while(true){
	//User setting
	User hostAccount = tw.showUser("@IPLSota");
	User newsAccount = tw.showUser("@nhk_news");
	//Loop test
	count++;
	System.out.println("count:" + count);

if(tempBl){
	Status hostStatus = hostAccount.getStatus();
	tweet = hostStatus.getText();
	
	System.out.println("tempmode...please tweet task");
	
	if(tweet.equals("speak")){
		speakBl = true;
		tempBl = false;
	}else if(tweet.equals("news")){
		newsBl = true;
		tempBl = false;
	}else if(tweet.equals("pose")){
		poseBl = true;
		tempBl = false;
	}else if(tweet.equals("photo")){
		//tempBl = true;
	}
}
if(speakBl){
	Status hostStatus = hostAccount.getStatus();
	tweet = hostStatus.getText();
	CRobotUtil.wait((int)6000);
	switch(tweet){
		case "news":
			speakBl = false;
			newsBl = true;
			break;
		case "pose":
			speakBl = false;
			newsBl = true;
			break;
		case "photo":
			speakBl = false;
			newsBl = true;
			break;
		default:
			if(tweetId != hostStatus.getId()){
			tweetId = hostStatus.getId();
			System.out.println("Sota found new tweet");
			String file = TextToSpeechSota.getTTSFile(tweet,(int)11,(int)13,(int)11);
			if(file!=null) CPlayWave.PlayWave(file,true);
			}else{
			System.out.println("Sota could't find new tweet");
			}
	}
}
if(newsBl){
	Status hostStatus = hostAccount.getStatus();
	tweet = hostStatus.getText();
	CRobotUtil.wait((int)6000);
	switch(tweet){
		case "speak":
			newsBl = false;
			speakBl = true;
			break;
		case "pose":
			newsBl = false;
			poseBl = true;
			break;
		case "photo":
			newsBl = false;
			photoBl = true;
			break;
		default:
			Status newsStatus = newsAccount.getStatus();
			tweet = newsStatus.getText();
			String[] news = tweet.split("#", -1);
			if(tweetId != newsStatus.getId()){
			tweetId = newsStatus.getId();
			System.out.println("Sota found new news");
			String file = TextToSpeechSota.getTTSFile(news[0],(int)11,(int)13,(int)11);
			if(file!=null) CPlayWave.PlayWave(file,true);
			}else{
			System.out.println("Sota could't find new news");
			}
	}
}
if(poseBl){
	Status hostStatus = hostAccount.getStatus();
	tweet = hostStatus.getText();
	CRobotUtil.wait((int)6000);
	switch(tweet){
		case "speak":
			poseBl = false;
			speakBl = true;
			break;
		case "news":
			poseBl = false;
			newsBl = true;
			break;
		case "photo":
			poseBl = false;
			photoBl = true;
			break;
		default:
			if(tweetId != hostStatus.getId()){
			tweetId = hostStatus.getId();
			switch(tweet){
				case "まっすぐ":
				case "デフォルト":
				case "default":
					System.out.println("Sota found new action");
					GlobalVariable.motion.play(defaultPose,1000);
					CRobotUtil.wait(1000);
					break;
				case "ひだり":
				case "左":
				case "left":
					System.out.println("Sota found new action");
					GlobalVariable.motion.play(leftUpPose,1000);
					CRobotUtil.wait(1000);
					break;
				case "みぎ":
				case "右":
				case "right":
					System.out.println("Sota found new action");
					GlobalVariable.motion.play(rightUpPose,1000);
					CRobotUtil.wait(1000);
					break;
				case "てれる":
				case "照れる":
					System.out.println("Sota found new action");
					GlobalVariable.motion.play(beShyPose,1000);
					CRobotUtil.wait(1000);
					break;
				default:
				System.out.println("Sota could't find new action");
			}
			}else{
			System.out.println("Sota could't find new action");
			}
	}
}
if(photoBl){
	GlobalVariable.motion.play(defaultPose,1000);
	CRobotUtil.wait((int)1000);

	System.out.println("Take photo standby...");
	String photoCount = TextToSpeechSota.getTTSFile("５、４、３、２、１、",(int)6,(int)13,(int)11);
	if(photoCount!=null) CPlayWave.PlayWave(photoCount,true);
	
	time_string = CRobotUtil.getTimeString();
	String filepath = "/var/sota/photo/";
	filepath += "photo" + " at " + "[" + time_string + "]";
	boolean isTrakcing=GlobalVariable.robocam.isAliveFaceDetectTask();
	if(isTrakcing) GlobalVariable.robocam.StopFaceTraking();
	GlobalVariable.robocam.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_5Mpixel, CameraCapture.CAP_FORMAT_MJPG));
	GlobalVariable.robocam.StillPicture(filepath);

	CRobotUtil.Log("stillpicture","save picthre file to \"" + filepath +"\"");
	if(isTrakcing) GlobalVariable.robocam.StartFaceTraking();
	
	photoBl = false;
	tempBl = true;
	
	}
}
}catch(TwitterException te){
System.out.println(te);
}