import SwiftUI
import Shared

struct MainView: View {
    @State private var number: String
    @State private var description: String
    @State private var isLoading: Bool
    @State private var errorMessage: String? = nil
    
    let viewModel: NumbersInfoViewModel

    init(number: String = "", description: String = "", isLoading: Bool = false) {
        self._number = State(initialValue: number)
        self._description = State(initialValue: description)
        self._isLoading = State(initialValue: isLoading)
        
        setupDI()
        viewModel = DiComponent().numbersInfoViewModel()
        viewModel.loadNumberInfo()
    }
    
    var body: some View {
        ZStack {
            Color(hex: "#1B1B2F")
                .edgesIgnoringSafeArea(.all)

            VStack {
                Spacer()

                Text(number)
                    .font(.system(size: 140))
                    .foregroundColor(Color(hex: "#E5E5E5"))
                    .padding(.bottom, 20)

                Text(description)
                    .font(.system(size: 22))
                    .foregroundColor(Color(hex: "#A3A3C2"))
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 12)
                    .padding(.bottom, 20)

                Spacer()
                
                HStack {
                    Button(action: {
                        viewModel.loadPreviousNumber()
                    }) {
                        Text("←")
                            .font(.system(size: 20, weight: .bold))
                            .foregroundColor(Color(hex: "#F9F9F9"))
                            .frame(width: 55, height: 55)
                            .background(Color(hex: "#3F72AF"))
                            .cornerRadius(8)
                    }
                    .padding(.leading, 12)

                    Spacer()
                    
                    Button(action: {
                        viewModel.generateNewNumber()
                    }) {
                        Text("Next Number")
                            .font(.system(size: 20))
                            .foregroundColor(Color(hex: "#F9F9F9"))
                            .padding(.horizontal, 28)
                            .padding(.vertical, 18)
                            .background(Color(hex: "#3F72AF"))
                            .cornerRadius(8)
                    }
                    
                    Spacer()
                    
                    Spacer()
                        .frame(width: 55)
                }

                Spacer()
            }

            if errorMessage != nil {
                VStack {
                    Spacer()

                    HStack {
                        Text(errorMessage!)
                            .foregroundColor(.white)
                            .padding()
                    }
                    .background(Color.red)
                    .cornerRadius(8)
                    .padding()
                    .transition(.slide)
                    .animation(.easeInOut)
                }
            }
            
            if isLoading {
                Color(hex: "#583F51B5")
                    .edgesIgnoringSafeArea(.all)
                    .opacity(0.5)
                ProgressView()
                    .scaleEffect(10)
            }
        }
        .task {
            for await error in viewModel.error {
                onError(error)
            }
        }
        .task {
            for await state in viewModel.state {
                switch state {
                    case is NumbersInfoState.Initial:
                        isLoading = false
                        errorMessage = nil
                    case is NumbersInfoState.Loading:
                        isLoading = true
                        errorMessage = nil
                    case let successState as NumbersInfoState.Success:
                        isLoading = false
                        number = "\(successState.numberInfo.number)"
                        description = successState.numberInfo.info
                        errorMessage = nil
                    case let error as NumbersInfoState.Error:
                        onError(error)
                    default:
                        break
                }
            }
        }
    }
    
    private func onError(_ error: NumbersInfoState.Error) {
        isLoading = false
        errorMessage = error.message
        DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
            errorMessage = nil
        }
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView(
            number: "42",
            description: "Answer to the Ultimate Question of Life, the Universe, and Everything"
        )
    }
}

extension Color {
    init(hex: String) {
        let scanner = Scanner(string: hex)
        scanner.scanLocation = 1
        var rgbValue: UInt64 = 0
        scanner.scanHexInt64(&rgbValue)
        let red = Double((rgbValue & 0xff0000) >> 16) / 255.0
        let green = Double((rgbValue & 0xff00) >> 8) / 255.0
        let blue = Double(rgbValue & 0xff) / 255.0
        self.init(red: red, green: green, blue: blue)
    }
}
