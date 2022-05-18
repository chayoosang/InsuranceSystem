import Auth.Login;
import Contract.InsuranceContract;
import Contract.InsuranceContractListImpl;
import Insurance.Insurance;
import Insurance.InsuranceListImpl;
import Policyholder.Policyholder;
import Policyholder.PolicyholderListImpl;
import Staff.CompensationManagement;
import Staff.Design;
import Staff.*;
import Staff.Staff.Department;
import Staff.StaffListImpl;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		Login login = new Login();
		Staff staff = null;
		StaffListImpl staffList = new StaffListImpl();
		InsuranceListImpl insuranceList = new InsuranceListImpl();
		PolicyholderListImpl policyholderList = new PolicyholderListImpl();
		InsuranceContractListImpl insuranceContractList = new InsuranceContractListImpl();

		main:
		while (true) {


			login:
			while (true) {
				System.out.println("전과자들 보험사 프로그램 \n");
				System.out.println("1. 로그인");
				System.out.println("2. 회원가입");

				String select1 = sc.next();

				if (Integer.parseInt(select1) == 1) {
					// 사원 로그인
					System.out.println("id : ");
					String inputId = sc.next();
					System.out.println("pw : ");
					String inputPW = sc.next();

					staff = login.login(Integer.parseInt(inputId), inputPW, staffList);

					if (staff != null) {
						System.out.println(staff.getId()
								+ " " + staff.getDepartment()
								+ " " + staff.getName()
								+ "님 환영합니다!");
						break login;
					} else {
						System.out.println("등록된 정보가 없습니다. 다시 시도해주시거나 회원 가입 후 진행 해주세요");
						continue login;
					}
				} else if (Integer.parseInt(select1) == 2) {
					// 사원 가입
					join:
					while (true) {
						System.out.println("신규 사원 등록");

						System.out.println("사원 부서 1. 설계 / 2. 영업 / 3. 보상운영");

						String department = sc.next();
						System.out.println("이름 : ");
						String name = sc.next();
						System.out.println("SSN : ");
						String ssn = sc.next();
						System.out.println("성별 : 1. 남자 / 2. 여자");
						String gender = sc.next();
						System.out.println("Email : ");
						String email = sc.next();
						System.out.println("Phone : ");
						String phone = sc.next();

						staff = login.createStaff(name, ssn, gender, email, phone, department, staffList);

						if (staff != null) {
							System.out.println("가입을 축하합니다 " + staff.getName() + "님!");
							System.out.println("ID는 " + staff.getId() + "이며 기본 PW는" + staff.getPassword() + "입니다.");
							break login;
						} else {
							System.out.println("가입에 실패하였습니다. 다시 시도해주세요.");
							continue join;
						}
					}

				}
			}
			selectWork:
			while (true) {
				System.out.println("1. 보험 관리");
				System.out.println("2. 인수 심사");
				System.out.println("3. 사원 관리");
				System.out.println("4. 보상 운영");
				System.out.println("5. 보험가입자 관리");
				System.out.println("6. 보험 계약 관리");
				System.out.println("7. 로그아웃");

				String select = sc.next();

				if (select.equals("1")) {

						if (staff.getDepartment() == Department.Design) {
							Design design = (Design) staff;

							design:
							while (true) {
								System.out.println("자사의 총 보험 개수 : " + insuranceList.getInsuranceList().size());
								System.out.println("자사의 인가받은 보험의 개수 : " + design.computeAuthorizeCount(insuranceList.getInsuranceList()));
								System.out.println("자사의 인가받지 못한 보험의 개수 : " + (insuranceList.getInsuranceList().size() - design.computeAuthorizeCount(insuranceList.getInsuranceList())));


								System.out.println("1. 보험 목록 조회하기");
								System.out.println("2. 뒤로가기");

								String select2 = sc.next();


								if (select2.equals("1")) {
									for (Insurance insurance : insuranceList.getInsuranceList()) {
										System.out.println(insurance.getId() + " " + insurance.getName() + " " + insurance.getExplanation() + " " + insurance.getAuthorizationDate());
									}
									System.out.println("1. 새로 만들기");
									System.out.println("2. 인가 받기");

									String select3 = sc.next();

									if (select3.equals("1")) {
										createInsurance:
										while (true) {
											System.out.println("보험 이름 : ");
											String name = sc.next();
											System.out.println("보험 설명 : ");
											String explanation = sc.next();
											System.out.println("보험료 : ");
											String premium = sc.next();

											System.out.println("해당 보험 정보가 맞습니까?\n"
													+ " 보험 이름 : " + name + "\n"
													+ " 보험 설명 : " + explanation + "\n"
													+ " 보험료 : " + premium + "\n"
													+ " 1. 예 2. 아니요 ");
											String answer = sc.next();
											if (answer.equals("1")) {
												design.design(name, explanation, premium, insuranceList);
												System.out.println("보험 생성이 완료되었습니다. 보험 관리 화면에서 인가를 받아야 해당 보험을 이용할 수 있습니다.");
												continue design;
											} else if (answer.equals("2")) {
												continue createInsurance;
											}
										}
									} else if (select3.equals("2")) {
										for (Insurance insurance : insuranceList.getInsuranceList()) {
											if (!insurance.isAuthorization()) {
												System.out.println(insurance.getId() + " "
														+ insurance.getName() + " " + insurance.getCreatedDate() + " "
														+ insurance.getExplanation() + "인가 여부 : " + insurance.isAuthorization());

												System.out.println("아직 인가 되지 않은 보험입니다. 해당 보험을 인가하시겠습니까?\n" +
														" 1. 예 2. 아니요");
												String answer = sc.next();

												if (answer.equals("1")) {
													System.out.println("해당 보험을 인가합니다.");
													design.authorize(insurance, insuranceList);
												} else {
													continue;
												}
											}
											continue design;
										}
									}
								} else if (select2.equals("2")) {
									continue selectWork;
								}else {
									System.out.println("목록에 있는 항목을 선택해주세요.");
									continue design;
								}
							}
						}else {
							System.out.println("죄송합니다. 해당 권한이 없습니다.\n" +
									"다시 시도해주세요.");
							continue selectWork;
						}
				}else if (select.equals("2")) {
					if (staff.getDepartment() == Department.Design) {

					} else {
						System.out.println("죄송합니다. 해당 권한이 없습니다.\n" +
								"다시 시도해주세요.");
					continue selectWork;
					}
				} else if (Integer.parseInt(select) == 3) {
					if (staff.getDepartment() == Department.Design) {

					} else {
						System.out.println("죄송합니다. 해당 권한이 없습니다.\n" +
								"다시 시도해주세요.");
						continue selectWork;
					}
				} else if (Integer.parseInt(select) == 4) {
					if (staff.getDepartment() == Department.CompensationManagement) {
						CompensationManagement compensationManagement = (CompensationManagement) staff;
						compensation:
						while (true) {
							System.out.println("1. 검색하기\n" +
									"2. 뒤로가기");
							String selectCompensation = sc.next();
							if (selectCompensation.equals("1")) {
								findContract:
								while (true) {
									System.out.println("보험 가입자 id : ");
									String policyholderId = sc.next();
									System.out.println("보험 가입자 이름 : ");
									String policyholderName = sc.next();

									if (policyholderId.isEmpty() || policyholderName.isEmpty()) {
										System.out.println("보험 가입자의 정보를 모두 입력하고 시도해주세요.");
										continue findContract;
									} else {
										InsuranceContractListImpl policyholderContractsList = compensationManagement.findInsuranceContracts(policyholderId, policyholderName, insuranceContractList);

										for (InsuranceContract insuranceContract : policyholderContractsList.getInsuranceContractList()) {
											System.out.println( insuranceContract.getInsurance().getId() + " 보험 이름 : " + insuranceContract.getInsurance().getName());
										}
										System.out.println("보험을 선택해주세요.");
										String selectInsuranceContractText = sc.next();

										InsuranceContract selectInsuranceContract = null;
										for (InsuranceContract insuranceContract : policyholderContractsList.getInsuranceContractList()) {
											if (Integer.parseInt(selectInsuranceContractText) == insuranceContract.getContractId()) {
												selectInsuranceContract = insuranceContract;
											}
										}
										if (selectInsuranceContract != null) {
											String selectCause, selectOccurringArea, selectOriginator = null;
											String brokenCondition, humanDamage, surroundingDamage;
											createAccident:
											while (true) {
												System.out.println("사고 발생의 주체를 선택해 주세요.\n" +
														"1. 보험 가입자\n" +
														"2. 사고\n" +
														"3. 자연재해\n");

												selectCause = sc.next();
												if (!(Integer.parseInt(selectCause) > 0 && Integer.parseInt(selectCause) < 4)) {
													System.out.println("목록에 있는 항목을 선택해주세요.");
													continue createAccident;
												} else {
													if (selectCause.equals("1")) {
														System.out.println("사고 발생의 원인을 선택해 주세요.\n" +
																"4. 고의적으로 발생한 사고\n" +
																"5. 중대한 과실로 발생한 사고\n" +
																"6. 경미한 과실(실수)로 발생한 사고\n");

														selectOriginator = sc.next();

														if (!(Integer.parseInt(selectOriginator) > 3 && Integer.parseInt(selectOriginator) < 7)) {
															System.out.println("목록에 있는 항목을 선택해주세요.");
															continue createAccident;
														} else {
															if (Integer.parseInt(selectOriginator) == 4) {
																System.out.println("죄송합니다. 고의적으로 발생한 사고는 보험금지급 의무 면책 사유입니다.");
																continue compensation;
															} else if (Integer.parseInt(selectOriginator) == 5) {
																System.out.println("죄송합니다. 중대한 과실로 발생한 사고는 보험금지급 의무 면책 사유입니다.");
																continue compensation;
															} else {
																break createAccident;
															}
														}
													} else if (Integer.parseInt(selectCause) == 3) {
														System.out.println("죄송합니다. 자연재해로 인한 발생한 사고는 보험금지급 의무 면책 사유입니다.");
														continue compensation;
													}
												}
											}
											if (selectOriginator != null) {
												selectOriginator:
												while (true) {
													System.out.println("사고 발생의 원인을 선택해 주세요.\n" +
															"1. 불 혹은 폭발로 인한 사고\n" +
															"2. 친족 혹은 고용인의 고의\n" +
															"3. 전기기기 또는 전기적 사고\n");

													selectOriginator = sc.next();
													if (!(Integer.parseInt(selectOriginator) > 0 && Integer.parseInt(selectOriginator) < 4)) {
														System.out.println("목록에 있는 항목을 선택해주세요.");
														continue selectOriginator;
													} else {
														if (Integer.parseInt(selectOriginator) == 2) {
															System.out.println("죄송합니다. 친족 혹은 고용인의 고의로 인한 발생한 사고는 보험금지급 의무 면책 사유입니다.");
															continue compensation;
														} else if (Integer.parseInt(selectOriginator) == 3) {
															System.out.println("죄송합니다. 전기기기 또는 전기적 사고로 인한 발생한 사고는 보험금지급 의무 면책 사유입니다.");
															continue compensation;
														} else {
															break selectOriginator;
														}
													}
												}
											}
											selectOccurringArea:
											while (true) {
												System.out.println("사고 발생의 장소 선택해 주세요.\n" +
														"1. 주택\n" +
														"2. 아파트\n" +
														"3. 오피스텔\n");
												selectOccurringArea = sc.next();
												if (!(Integer.parseInt(selectOccurringArea) > 0 && Integer.parseInt(selectOccurringArea) < 4)) {
													System.out.println("화재 장소는 주택, 아파트, 오피스텔 중 하나를 입력해주세요.");
													continue compensation;
												} else {
													break selectOccurringArea;
												}
											}

											createAccident:
											while (true) {
												System.out.println("화재 장소 파손 상태 (0~100) : ");
												brokenCondition = sc.next();
												System.out.println("인적 피해 (0~100) : ");
												humanDamage = sc.next();
												System.out.println("주변 피해 (0~100) : ");
												surroundingDamage = sc.next();

												if (!(Integer.parseInt(brokenCondition) >= 0 && Integer.parseInt(brokenCondition) <= 100)
														|| !((Integer.parseInt(humanDamage) >= 0 && Integer.parseInt(humanDamage) <= 100))
														|| !((Integer.parseInt(surroundingDamage) >= 0 && Integer.parseInt(surroundingDamage) <= 100))) {
													System.out.println("피해 정도는 0부터 100까지의 숫자로 입력해주세요.");
													continue createAccident;
												} else {
													compensationManagement.judgeIndemnification(selectCause, selectOriginator, selectOccurringArea,
															brokenCondition, humanDamage, surroundingDamage, selectInsuranceContract);
												}
											}
										} else {
											System.out.println("가입된 보험이 없습니다. 인수 심사 완료 후 다시 진행해 주세요.");
											continue compensation;
										}

									}
								}
							} else if (selectCompensation.equals("2")) {
								continue selectWork;
							} else {
								System.out.println("목록에 있는 항목을 선택해주세요.");
								continue compensation;
							}
						}
					} else {
						System.out.println("죄송합니다. 해당 권한이 없습니다.\n" +
								"다시 시도해주세요.");
						continue selectWork;
					}
				} else if (Integer.parseInt(select) == 5) {
					if (staff.getDepartment() == Department.Sales) {
						Sales sales = (Sales) staff;
						salesPoly:
						while (true) {
							System.out.println("총 보험 가입자 수 : " + insuranceContractList.getInsuranceContractList().size());
							System.out.println("이 달의 가입자 수 : " + sales.computeThisMonthJoin(insuranceContractList));
							System.out.println("미납한 보험 가입자 수 : " + sales.computePayment(insuranceContractList));
							System.out.println();
							System.out.println("1. 보험가입자 정보 상세보기");
							System.out.println("2. 돌아가기");

							String select5 = sc.next();

							if (select5.equals("1")) {
								for (Policyholder policyholder : policyholderList.getPolicyholderList()) {
									System.out.println(policyholder.getName() + " " + policyholder.getSSN() + " " + policyholder.getAddress()
											+ " " + policyholder.getPhoneNumber() + " " + policyholder.getMedicalHistory().getMyDisease().name() + " " + policyholder.isPay());
								}
								System.out.println();
								System.out.println("1. 보험료 납입받기");
								String selectPolicyholder = sc.next();
								if (selectPolicyholder.equals("2")) {
									if (sales.computePayment(insuranceContractList) != 0) {
										payment:
										while (true) {
											for (InsuranceContract insuranceContract : insuranceContractList.getInsuranceContractList()) {
												if (!insuranceContract.getPolicyholder().isPay()) {
													System.out.println(insuranceContract.getPolicyholder().getName() + " / " + insuranceContract.getPolicyholder().getSSN() + " / " + insuranceContract.getPolicyholder().getAddress()
															+ " / " + insuranceContract.getPolicyholder().getPhoneNumber() + " / " + insuranceContract.getPolicyholder().getEmail() + " / " + insuranceContract.getInsurance().getName()
															+ " / " + insuranceContract.getInsurancePrice());
												}
											}
											System.out.println("체납된 보험가입자" + sales.computePayment(insuranceContractList) + "명에게 청구서를 보내시겠습니까?");
											System.out.println("1. 확인  2. 취소");
											String select7 = sc.next();
											if (select7.equals("1")) {
												sales.insuracePayment(insuranceContractList);
												System.out.println("체납된 고객들에게 성공적으로 청구서를 전송하였습니다.");
												continue salesPoly;
											} else if (select.equals("2")) {
												System.out.println("청구서 전송을 취소하였습니다.");
												continue salesPoly;
											} else {
												System.out.println("목록에 있는 번호를 선택해주세요.");
												continue payment;
											}

										}
									} else {
										System.out.println("체납된 보험가입자가 없습니다.");
										continue salesPoly;
									}
								}

								continue selectWork;
							} else if (select5.equals("2")) {
								continue selectWork;
							}else {
								System.out.println("목록에 있는 항목을 선택해주세요.");
								continue salesPoly;
							}
						}
					}else {
						System.out.println("죄송합니다. 해당 권한이 없습니다.\n" +
								"다시 시도해주세요.");
						continue selectWork;
					}
				}else if (Integer.parseInt(select) == 6) {
						if (staff.getDepartment() == Department.Sales) {
							Sales sales = (Sales) staff;
							Policyholder policyholder = null;
							Insurance selectInsurance = null;

							adminContract:
							while (true) {
								System.out.println("1. 계약 체결하기");
								System.out.println("2. 계약 해지하기");
								System.out.println("3. 뒤로 가기");

								String answer = sc.next();

								if (answer.equals("1")) {
									for (Insurance insurance : insuranceList.getInsuranceList()) {
										System.out.println(insurance.getId() + " " + insurance.getName());
									}
									System.out.println("보험을 선택해 주세요.");
									String select6 = sc.next();

									for (Insurance insurance : insuranceList.getInsuranceList()) {
										if (select6.equals(String.valueOf(insurance.getId()))) {
											selectInsurance = insurance;
											break;
										}
									}

									if (selectInsurance != null) {
										System.out.println("화재보험 가입자 정보 입력을 시작합니다.");

										createPolicyholder:
										while (true) {
											System.out.println("고객명 : ");
											String name = sc.next();
											System.out.println("주민번호 : ");
											String SSN = sc.next();
											System.out.println("성별 : 1.남자  2.여자");
											String sex = sc.next();
											System.out.println("이메일 : ");
											String email = sc.next();
											System.out.println("휴대전화 : ");
											String phoneNumber = sc.next();
											System.out.println("계좌번호 : ");
											String account = sc.next();
											System.out.println("주소 : ");
											String address = sc.next();

											System.out.println("병력 : 1. 결핵  2. 수두  3. 홍역");
											String medicalName = sc.next();
											System.out.println("발병년도 : ");
											String medicalYear = sc.next();
											System.out.println("해당 병력의 완치 여부를 선택해주세요. 1.완치  2.불완치");
											String medicalCure = sc.next();


											if (!name.isEmpty() && !SSN.isEmpty() && !sex.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty() && !account.isEmpty()
													&& !address.isEmpty() && !medicalName.isEmpty() && !medicalYear.isEmpty() && !medicalCure.isEmpty()) {
												policyholder = sales.joinPolicyholder(name, SSN, sex, email, phoneNumber, account, address, medicalName, medicalYear, medicalCure, policyholderList);
												break createPolicyholder;

											} else {
												System.out.println("가입 정보를 모두 빠짐 없이 입력해주세요.");
												continue createPolicyholder;
											}
										}
										System.out.println("청약서에 동의 하시고 계약을 진행하시겠습니까?");
										System.out.println("1. 예  2. 아니요");
										String select10 = sc.next();
										if (select10.equals("1")) {
											sales.createContract(policyholder, selectInsurance, insuranceContractList);
											System.out.println("화재보험 계약 정보 입력이 완료되었습니다.");
											continue adminContract;
										} else if (select10.equals("2")) {
											System.out.println("계약을 취소하였습니다.");
											continue adminContract;
										}
									}

								} else if (answer.equals("2")) {
									InsuranceContractListImpl findContract = new InsuranceContractListImpl();
									findContract:
									while (true) {
										System.out.println("보험 가입자 ID : ");
										String id = sc.next();
										System.out.println("보험 가입자 이름 : ");
										String name = sc.next();

										sales.findContratct(id, name, insuranceContractList, findContract);

										if (!findContract.getInsuranceContractList().isEmpty()) {
											cancleContract:
											while (true) {
												for (InsuranceContract insuranceContract : findContract.getInsuranceContractList()) {
													System.out.println(insuranceContract.getContractId() + " " + insuranceContract.getInsurance().getName());
												}
												System.out.println("취소하실 계약을 선택해주세요.");
												String selectContract = sc.next();

												if (sales.deleteContract(selectContract, insuranceContractList, findContract)) {
													System.out.println("성공적으로 계약을 해지하였습니다.");
													continue adminContract;
												} else {
													System.out.println("목록에 있는 계약 중 하나를 선택해주세요.");
													continue cancleContract;
												}
											}
										} else {
											System.out.println("계약 중인 보험이 현재 없습니다. 다시 시도해주세요.");
											continue findContract;
										}
									}

								} else if (answer.equals("3")) {
									continue selectWork;
								} else {
									System.out.println("목록에 있는 항목을 선택해주세요.");
									continue adminContract;
								}
							}
						}else {
							System.out.println("죄송합니다. 해당 권한이 없습니다.\n" +"다시 시도해주세요.");
							continue selectWork;
						}
				} else if (select.equals("7")) {
					staff = null;
					continue main;
				}else {
					System.out.println("목록에 있는 항목을 선택해주세요.");
					continue selectWork;
				}

				}
			}

			}

		}











