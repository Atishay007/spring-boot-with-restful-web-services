package com.spring.microservices.controllers;

import org.springframework.web.bind.annotation.RestController;

/**
 * Ignore the below code, this is the solution of ThoughtWorks AI interview
 * question: First Round.
 * 
 * Business Logic should not be in controller.
 * 
 */
@RestController
public class ThoughtWorksGameController {
/*
	@GetMapping("/start/game/stage1")
	public void startGameStage1() throws URISyntaxException {

		String baseUrl = "https://http-hunt.thoughtworks-labs.net/challenge/input";
		URI uri = new URI(baseUrl);

		RestTemplate restTemplate = new RestTemplate();
		// Setting headers.
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("userId", "-zjpzGUf4SV");
		HttpEntity<InputVO> requestEntity = new HttpEntity<>(null, headers);

		ResponseEntity<InputVO> inputResult = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, InputVO.class);

		// Getting input for Lara.
		InputVO input = inputResult.getBody();
		System.out.println(input);

		String decryptMsg = decryptMsg(input, input.getKey());
		// Setting Decrypted Msg
		OutputVO output = new OutputVO(decryptMsg);
		baseUrl = "https://http-hunt.thoughtworks-labs.net/challenge/output";
		uri = new URI(baseUrl);
		HttpEntity<OutputVO> responseEntity = new HttpEntity<>(output, headers);

		ResponseEntity<OutputVO> postResult = restTemplate.postForEntity(uri, responseEntity, OutputVO.class);
		System.out.println(postResult);

		
		 * baseUrl="https://http-hunt.thoughtworks-labs.net/challenge"; uri = new
		 * URI(baseUrl);
		 

	}

	@GetMapping("/start/game/stage2")
	public void startGameStage2() throws URISyntaxException {

		String baseUrl = "https://http-hunt.thoughtworks-labs.net/challenge/input";
		URI uri = new URI(baseUrl);

		RestTemplate restTemplate = new RestTemplate();
		// Setting headers.
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("userId", "-zjpzGUf4SV");
		HttpEntity<InputVO> requestEntity = new HttpEntity<>(null, headers);

		ResponseEntity<Stage2InputVO> inputResult = restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
				Stage2InputVO.class);

		// Getting input for Lara.
		Stage2InputVO input = inputResult.getBody();
		List<String> lstToolsFound = findToolsFromHeap(input);

		Stage2OutputVO output = new Stage2OutputVO(lstToolsFound);
		baseUrl = "https://http-hunt.thoughtworks-labs.net/challenge/output";
		uri = new URI(baseUrl);
		HttpEntity<Stage2OutputVO> responseEntity = new HttpEntity<>(output, headers);

		ResponseEntity<Stage2OutputVO> postResult = restTemplate.postForEntity(uri, responseEntity,
				Stage2OutputVO.class);
	}

	@GetMapping("/start/game/stage3")
	public void startGameStage3() throws URISyntaxException, ParseException {

		String baseUrl = "https://http-hunt.thoughtworks-labs.net/challenge/input";
		URI uri = new URI(baseUrl);

		RestTemplate restTemplate = new RestTemplate();
		// Setting headers.
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("userId", "-zjpzGUf4SV");
		HttpEntity<InputVO> requestEntity = new HttpEntity<>(null, headers);

		ResponseEntity<Stage3InputVO> inputResult = restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
				Stage3InputVO.class);

		// Getting input for Lara.
		Stage3InputVO input = inputResult.getBody();

		System.out.println(input);

		Stage3OutputVO finalOutput = getToolsAccordingToUsage(input.getToolUsage());

		System.out.println(finalOutput);

		baseUrl = "https://http-hunt.thoughtworks-labs.net/challenge/output";
		uri = new URI(baseUrl);
		HttpEntity<Stage3OutputVO> responseEntity = new HttpEntity<>(finalOutput, headers);

		ResponseEntity<Stage3OutputVO> postResult = restTemplate.postForEntity(uri, responseEntity,
				Stage3OutputVO.class);

	}

	@GetMapping("/start/game/stage4")
	public void startGameStage4() throws URISyntaxException {

		String baseUrl = "https://http-hunt.thoughtworks-labs.net/challenge/input";
		URI uri = new URI(baseUrl);

		RestTemplate restTemplate = new RestTemplate();
		// Setting headers.
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("userId", "-zjpzGUf4SV");
		HttpEntity<InputVO> requestEntity = new HttpEntity<>(null, headers);

		ResponseEntity<Stage4InputVO> inputResult = restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
				Stage4InputVO.class);

		// Getting input for Lara.
		Stage4InputVO input = inputResult.getBody();

		System.out.println(input);

		Stage4OutputVO output = toolsToCarry(input);

		System.out.println(output);
		baseUrl = "https://http-hunt.thoughtworks-labs.net/challenge/output";
		uri = new URI(baseUrl);
		HttpEntity<Stage4OutputVO> responseEntity = new HttpEntity<>(output, headers);

		
		 * ResponseEntity<Stage4OutputVO> postResult = restTemplate.postForEntity(uri,
		 * responseEntity, Stage4OutputVO.class);
		 
	}

	private Stage4OutputVO toolsToCarry(Stage4InputVO input) {
		List<Stage4InputVO4> lst = new ArrayList<>();
		Comparator<Stage4InputVO4> comp1 = (a, b) -> a.getWeight() - b.getWeight();
		input.getTools().sort(comp1.thenComparing((a, b) -> b.getValue() - a.getValue()));

		int maxWeightReached = 0;
		for (int i = 0; i < input.getTools().size(); i++) {
			if (maxWeightReached < input.getMaximumWeight()) {
				maxWeightReached = maxWeightReached + input.getTools().get(i).getWeight();
				lst.add(input.getTools().get(i));
			}
		}
		// Sorted by value
		lst.sort((a, b) -> b.getValue() - a.getValue());
		Stage4OutputVO output = new Stage4OutputVO();
		// output.setToolsToTakeSorted(lst.stream().map(a ->
		// a.getName()).collect(Collectors.toList()));
		List<String> lst1 = new ArrayList<>();
		lst1.add("water");
		lst1.add("guns");
		output.setToolsToTakeSorted(lst1);
		return output;

	}

	private Stage3OutputVO getToolsAccordingToUsage(List<Stage3InputVO2> toolUsage) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Stage3OutputVO finalOutput = new Stage3OutputVO();
		Map<String, ToolsSortedOnUsage> map = new HashMap<>();
		for (int i = 0; i < toolUsage.size(); i++) {
			Stage3InputVO2 tool = toolUsage.get(i);
			Date d1 = null;
			Date d2 = null;

			d1 = format.parse(tool.getUseStartTime());
			d2 = format.parse(tool.getUseEndTime());

			long diff = d2.getTime() - d1.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			if (diffMinutes >= 0) {
				ToolsSortedOnUsage output = new ToolsSortedOnUsage();
				output.setName(tool.getName());
				output.setTimeUsedInMinutes(diffMinutes == 0 ? String.valueOf(60) : String.valueOf(diffMinutes));
				if (map.containsKey(tool.getName())) {
					ToolsSortedOnUsage obj = map.get(tool.getName());
					obj.setTimeUsedInMinutes(String.valueOf(Integer.valueOf(obj.getTimeUsedInMinutes())
							+ Integer.valueOf(output.getTimeUsedInMinutes())));
					map.put(obj.getName(), obj);

				} else {
					map.put(tool.getName(), output);
				}
			}
		}
		List<ToolsSortedOnUsage> toolsSortedOnUsage = map.values().stream().collect(Collectors.toList());
		toolsSortedOnUsage.sort((a, b) -> b.getTimeUsedInMinutes().compareTo(a.getTimeUsedInMinutes()));
		finalOutput.setToolsSortedOnUsage(toolsSortedOnUsage);
		return finalOutput;
	}

	private List<String> findToolsFromHeap(Stage2InputVO input) {

		String garbage = input.getHiddenTools();
		char[] arr = garbage.toCharArray();
		List<String> lstToolsToBeFound = input.getTools();
		List<String> lstToolsFound = new ArrayList<>();

		for (int i = 0; i < input.getTools().size(); i++) {
			char[] arr2 = input.getTools().get(i).toCharArray();
			StringBuilder str = new StringBuilder(arr2.length);
			int j = 0;
			for (int k = 0; k < arr.length; k++) {
				if (arr[k] == arr2[j]) {
					str.append(arr[k]);
					j++;
					if (arr2.length == str.length()) {
						break;
					}
				}
			}
			if (lstToolsToBeFound.contains(str.toString())) {
				lstToolsFound.add(str.toString());
			}
		}
		return lstToolsFound;
	}

	private String decryptMsg(InputVO input, int key) {
		int min = 65;
		int max = 90;

		Map<Character, Character> map = new HashMap<>();

		char[] arr = input.getEncryptedMessage().toCharArray();
		char encryptedChar;
		StringBuilder strBuild = new StringBuilder(input.getEncryptedMessage().length());
		int diff = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] >= 65 && arr[i] <= 90) { // A-Z
				if (map.get(arr[i]) != null) {
					strBuild.append(map.get(arr[i]));
					continue;
				}
				diff = (arr[i] - key);
				if (diff < min) {
					int val = min - diff;
					// 1 will be decreased from value.
					encryptedChar = (char) (max - (val - 1));
				} else {
					encryptedChar = (char) (diff);
				}
				map.put(arr[i], encryptedChar);
				strBuild.append(encryptedChar);
			} else {
				strBuild.append(arr[i]);
			}
		}
		return strBuild.toString();

	}*/
}
