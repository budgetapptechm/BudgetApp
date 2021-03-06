package com.gene.app.action;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.gene.app.dao.CostCenterCacheUtil;
import com.gene.app.dao.DBUtil;
import com.gene.app.model.GtfReport;
import com.gene.app.model.ProjectParameters;
import com.gene.app.util.BudgetConstants;
import com.gene.app.util.Util;
import com.gene.app.ws.exception.ErrorObject;
import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class InitiateProjectServlet extends HttpServlet{
	private final static Logger LOGGER = Logger
			.getLogger(InitiateProjectServlet.class.getName());
	CostCenterCacheUtil costCenterCacheUtil = new CostCenterCacheUtil();
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("initializeURL");
		System.out.println("Start time"+System.currentTimeMillis());
		final String url = req.getRequestURL().toString();
        final String baseURL = url.substring(0, url.length()
                           - req.getRequestURI().length())
                           + req.getContextPath() + "/";
		String rcostCenter = req.getParameter("ccId");
		String rgMemId = req.getParameter("dummyGMemId");
		ProjectParameters prjParam = new ProjectParameters();
		List<String> costCenters = new ArrayList<String>();
		costCenters.add(rcostCenter);
		prjParam.setCostCentres(costCenters);
		String rUnixId = req.getParameter("unixId");
		prjParam.setProjectOwner(rUnixId);
		String rprj_name = req.getParameter("prj_name");
		prjParam.setProjectName(rprj_name);
		System.out.println("rcostCenter" + rcostCenter + "::::" + rgMemId
				+ "::::" + rprj_name + "::::" + rUnixId);
		storeRprtTogMemori(req, resp, prjParam,baseURL);
		System.out.println("End time"+System.currentTimeMillis());
	}

	public void storeRprtTogMemori(HttpServletRequest req,
			HttpServletResponse resp, ProjectParameters prjParam,String baseURL) throws IOException{
		Gson gson = new Gson();
		ErrorObject respFrmStudy = new ErrorObject();
		try {
			ArrayList<String> scopes = new ArrayList<>();
			scopes.add("https://www.googleapis.com/auth/userinfo.email");
			AppIdentityService appIdentity = AppIdentityServiceFactory
					.getAppIdentityService();
			AppIdentityService.GetAccessTokenResult accessToken = appIdentity
					.getAccessToken(scopes);
			
			String request = gson.toJson(prjParam);
			//String request = prepareInitiatePrjReqURL(ccId, unixId, prj_name);
			URL url = new URL(
					"https://memori-dev.appspot.com/web-service/project/preInitiate");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.addRequestProperty("Content-Type", "application/json");
			connection.addRequestProperty("Authorization", "OAuth "
					+ accessToken.getAccessToken());
			connection.setInstanceFollowRedirects(false);
			connection.setConnectTimeout(BudgetConstants.TIME_OUT_PERIOD);
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			LOGGER.log(Level.INFO, "Request :" + request.toString());
			System.out.println("request time"+System.currentTimeMillis());
			writer.write(request);
			writer.close();
			System.out.println("request end time"+System.currentTimeMillis());
			final int responseCode = connection.getResponseCode();
			LOGGER.log(Level.INFO, "Response Code is" + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {
				//final long newGMemId = resp.getInt("statusCode");
				JSONTokener response_tokens = new JSONTokener(connection.getInputStream());
				JSONObject response = new JSONObject();
				String errString="";
				
				try {
					response = new JSONObject(response_tokens);
					respFrmStudy = gson.fromJson(response.toString(), ErrorObject.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					errString = "No result found.";
					e.printStackTrace();
				}

				try {
					respFrmStudy.getStatusMessage();
				} catch (RuntimeException ex) {
				}
				try {
					//final long gMemoriId = response.getInt("statusCode");
					String result = String.valueOf(respFrmStudy.getStatusMessage());
					System.out.println("gMemoriId is:::"+respFrmStudy.getNewGMemId());
				} catch (RuntimeException ex) {
				}
				System.out.println("req.getParameter(dummyGMemId)"+req.getParameter("dummyGMemId"));
				System.out.println("prjParam.getCostCentres().get(0)"+prjParam.getCostCentres().get(0));
				System.out.println("respFrmStudy.getNewGMemId()"+respFrmStudy.getNewGMemId());
				System.out.println("error msg"+respFrmStudy.getStatusMessage());
				System.out.println("error code"+respFrmStudy.getStatusCode());
				if(Util.isNullOrEmpty(respFrmStudy.getNewGMemId())){
				updateGMemoriIdInBudget(req.getParameter("dummyGMemId"),prjParam.getCostCentres().get(0),respFrmStudy.getNewGMemId(),baseURL);
				//resp.sendRedirect("https://memori-dev.appspot.com/initiateProject?gMemoriId="+respFrmStudy.getNewGMemId());
				req.setAttribute("gMemoriId", respFrmStudy.getNewGMemId());
				req.setAttribute("Error Code", respFrmStudy.getStatusCode());
				req.setAttribute("Error Msg", respFrmStudy.getStatusMessage());
				}else{
					//throw new Exception("Project was not created in study. Error reason: "+respFrmStudy.getStatusMessage());
					req.setAttribute("Error Code", respFrmStudy.getStatusCode());
					req.setAttribute("Error Msg", "Project was not created in study. Error reason: "+respFrmStudy.getStatusMessage());
				}
				gson = new Gson();
				resp.getWriter().write(gson.toJson(respFrmStudy));
				/*RequestDispatcher rd = req.getRequestDispatcher("/getreport");
				rd.forward(req, resp);*/
			} else {
				//throw new Exception("Response Code is : "+responseCode);
				respFrmStudy.setStatusCode(connection.getResponseCode());
				respFrmStudy.setStatusMessage(connection.getResponseMessage());
				resp.getWriter().write(gson.toJson(respFrmStudy));
			}

		} catch (Exception e) {
			// Error handling elided.
			String result = e.getMessage();
			System.out.println("result:::" + result);
			respFrmStudy.setStatusCode(414);
			respFrmStudy.setStatusMessage(result);
			resp.getWriter().write(gson.toJson(respFrmStudy));
			req.setAttribute("Error Msg",  e.getMessage() +"::::"+e.getStackTrace());
		}

	}

	public void updateGMemoriIdInBudget(String gMemoriId,String costCenter,String gMemIdFrmStudy,String baseURL){
		DBUtil util = new DBUtil();
		Map<String,GtfReport> gtfRptMap = util.getAllReportDataFromCache(costCenter);
		List<GtfReport> gtfRptList = new ArrayList<GtfReport>();
		List<GtfReport> oldgtfRptList = new ArrayList<GtfReport>();
		String newGMemId = "";
		GtfReport gtfRpt = new GtfReport();
		ArrayList<String> newChildList = null;
		List<String> oldChildList = new ArrayList<String>();
		
		if(gtfRptMap!=null && !gtfRptMap.isEmpty() && gtfRptMap.get(gMemoriId)!=null ){
			if(gtfRptMap.get(gMemoriId).getMultiBrand() && gtfRptMap.get(gMemoriId).getChildProjectList()!=null){
			List<String> cList  = gtfRptMap.get(gMemoriId).getChildProjectList();
			for(String childId : cList){
				newGMemId = "";
				if(childId.equals(gMemoriId) || childId.contains(gMemoriId)){
					if(childId.contains(gMemoriId) && childId.contains(".")){
						newGMemId=gMemIdFrmStudy+"."+childId.substring(childId.indexOf(".")+1);//split(".")[1];
					}else if(childId.equals(gMemoriId)){
						newGMemId = gMemIdFrmStudy;
					}
					try {
						gtfRpt =  gtfRptMap.get(childId);
						oldgtfRptList.add((GtfReport)gtfRpt.clone());
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gtfRpt.setgMemoryId(newGMemId);
					gtfRpt.setDummyGMemoriId(false);
					oldChildList = gtfRpt.getChildProjectList();
					newChildList = new ArrayList<String>();
					if(oldChildList != null && !oldChildList.isEmpty()){
						for(String chld : oldChildList ){
							newChildList.add(chld.contains(".")?(gMemIdFrmStudy+chld.substring(chld.indexOf("."))):gMemIdFrmStudy);
						}
						gtfRpt.setChildProjectList(newChildList);
					}
					gtfRptList.add(gtfRpt);
				}
			}
			}else{
				gtfRpt =  gtfRptMap.get(gMemoriId);
					oldgtfRptList.add(gtfRpt);
				gtfRpt.setgMemoryId(gMemIdFrmStudy);
				gtfRpt.setDummyGMemoriId(false);
				gtfRptList.add(gtfRpt);
			}
			if(oldgtfRptList!=null && !oldgtfRptList.isEmpty()){
				for(int i=0;i<oldgtfRptList.size();i++){
					gtfRptMap.remove(oldgtfRptList.get(i).getgMemoryId());
				}
			}
			if(gtfRptList!=null && !gtfRptList.isEmpty()){
				for(int i=0;i<gtfRptList.size();i++){
					gtfRptMap.put(gtfRptList.get(i).getgMemoryId(),gtfRptList.get(i));
				}
			}
			//System.out.println("gtfRptMap in update"+gtfRptMap);
			//System.out.println("gtfRptList in update"+gtfRptList.get(0).getgMemoryId());
			//util.removeExistingProject(oldgtfRptList,baseURL);
			//util.storeProjectsToCache(oldgtfRptList,gtfRptList,costCenter);
		//	util.storeProjectsToCache(gtfRptList,costCenter, BudgetConstants.NEW);
			
			System.out.println("Start of jdo time"+System.currentTimeMillis());
			util.generateProjectIdUsingJDOTxn(gtfRptList,gMemoriId,baseURL,costCenter);
			System.out.println("End of jdo time"+System.currentTimeMillis());
			System.out.println("gtfRptMap from cc = "+util.getAllReportDataFromCache(costCenter));
		}
		
	}
}
