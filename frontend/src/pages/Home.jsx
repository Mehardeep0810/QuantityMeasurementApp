import { useEffect, useState } from "react"
import axios from "axios"

//const API = "http://localhost:8080/api"
const API =
  "https://quantitymeasurement-backend.onrender.com/api"

const measurementUnits = {
  LENGTH: ["FEET", "INCH", "YARD", "CENTIMETER", "METER"],
  WEIGHT: ["KILOGRAM", "GRAM", "POUND"],
  VOLUME: ["LITRE", "MILLILITRE", "GALLON"],
  TEMPERATURE: ["CELSIUS", "FAHRENHEIT", "KELVIN"],
}

function Home() {
  const [token, setToken] = useState(
    localStorage.getItem("jwt") || ""
  )

  const [result, setResult] = useState("")

  const [type, setType] = useState("LENGTH")

  const [value1, setValue1] = useState("")
  const [unit1, setUnit1] = useState("FEET")

  const [value2, setValue2] = useState("")
  const [unit2, setUnit2] = useState("INCH")

  const [targetUnit, setTargetUnit] = useState("FEET")

  useEffect(() => {
    const units = measurementUnits[type]

    setUnit1(units[0])
    setUnit2(units[1] || units[0])
    setTargetUnit(units[0])
  }, [type])

  const login = async () => {
    try {
      const response = await axios.get(
        `${API}/auth/login`,
        {
          withCredentials: true,
        }
      )

      if (response.data.jwt) {
        localStorage.setItem(
          "jwt",
          response.data.jwt
        )

        setToken(response.data.jwt)
      }
    } catch (err) {
      alert(
        "Use browser directly:\nhttp://localhost:8080/api/auth/login"
      )
    }
  }

  const getHeaders = () => ({
    Authorization: `Bearer ${localStorage.getItem(
      "jwt"
    )}`,
    "Content-Type": "application/json",
  })

  const createDTO = (value, unit) => ({
    value: Number(value),
    unit,
    type,
  })

  const performOperation = async (operation) => {
    try {
      let payload = {}

      if (operation === "convert") {
        payload = {
          source: createDTO(value1, unit1),
          targetUnit,
        }
      } else if (operation === "divide") {
        payload = {
          a: createDTO(value1, unit1),
          b: createDTO(value2, unit2),
        }
      } else {
        payload = {
          thisQuantityDTO: createDTO(
            value1,
            unit1
          ),
          thatQuantityDTO: createDTO(
            value2,
            unit2
          ),
          targetUnit,
        }
      }

      const response = await axios.post(
        `${API}/v1/quantities/${operation}`,
        payload,
        {
          headers: getHeaders(),
        }
      )

      setResult(
        JSON.stringify(response.data, null, 2)
      )
    } catch (err) {
      console.log(err)

      if (err.response?.data) {
        setResult(
          JSON.stringify(
            err.response.data,
            null,
            2
          )
        )
      } else {
        setResult("Server Error")
      }
    }
  }

  const disableTemperatureArithmetic =
    type === "TEMPERATURE"

  return (
    <div
      style={{
        minHeight: "100vh",
        background:
          "linear-gradient(to right,rgb(13, 27, 66),rgb(21, 40, 82))",
        padding: "40px",
        color: "white",
        fontFamily: "Arial",
      }}
    >
      <div
        style={{
          maxWidth: "1200px",
          margin: "auto",
        }}
      >
        {/* TITLE */}
        <h1
          style={{
            textAlign: "center",
            fontSize: "50px",
            marginBottom: "10px",
            color: "white",
            fontWeight: "bold",
          }}
        >
          Quantity Measurement System
        </h1>

        <p
          style={{
            textAlign: "center",
            opacity: 0.8,
            marginBottom: "40px",
            fontSize: "20px",
          }}
        >
          Spring Boot + React + JWT Authentication
        </p>

        {/* JWT SECTION */}
        <div
          style={{
            background: "rgba(255,255,255,0.1)",
            padding: "25px",
            borderRadius: "20px",
            marginBottom: "30px",
            backdropFilter: "blur(10px)",
          }}
        >
          <button
            onClick={login}
            style={{
              padding: "14px 30px",
              border: "none",
              borderRadius: "12px",
              background: "#22c55e",
              color: "white",
              fontSize: "18px",
              cursor: "pointer",
              fontWeight: "bold",
            }}
          >
            Authorize & Get JWT Token
          </button>

          <div
            style={{
              marginTop: "25px",
            }}
          >
            <strong
              style={{
                fontSize: "20px",
              }}
            >
              JWT Token
            </strong>

            <textarea
              value={token}
              onChange={(e) => {
                setToken(e.target.value)

                localStorage.setItem(
                  "jwt",
                  e.target.value
                )
              }}
              placeholder="Paste JWT token here"
              style={{
                width: "95%",
                height: "80px",
                marginTop: "15px",
                padding: "15px",
                borderRadius: "12px",
                border: "none",
                fontSize: "12px",
                resize: "none",
                overflowWrap: "break-word",
                wordBreak: "break-all",
                background: "#091224",
                color: "#00ff88",
                fontFamily: "monospace",
                outline: "none",
              }}
            />

            <br />

            <button
              onClick={() => {
                localStorage.setItem(
                  "jwt",
                  token
                )

                alert("JWT Token Saved!")
              }}
              style={{
                marginTop: "15px",
                padding: "12px 22px",
                border: "none",
                borderRadius: "10px",
                background: "#2563eb",
                color: "white",
                fontWeight: "bold",
                cursor: "pointer",
                fontSize: "16px",
              }}
            >
              Save Token
            </button>
          </div>
        </div>

        {/* MAIN GRID */}
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "1fr 1fr",
            gap: "25px",
          }}
        >
          {/* LEFT PANEL */}
          <div
            style={{
              background: "rgba(255,255,255,0.1)",
              padding: "25px",
              borderRadius: "20px",
              backdropFilter: "blur(10px)",
            }}
          >
            <h2>Measurement Settings</h2>

            <label>Measurement Type</label>

            <select
              value={type}
              onChange={(e) =>
                setType(e.target.value)
              }
              style={inputStyle}
            >
              <option value="LENGTH">
                Length
              </option>

              <option value="WEIGHT">
                Weight
              </option>

              <option value="VOLUME">
                Volume
              </option>

              <option value="TEMPERATURE">
                Temperature
              </option>
            </select>

            <h3>First Quantity</h3>

            <input
              type="number"
              placeholder="Value"
              value={value1}
              onChange={(e) =>
                setValue1(e.target.value)
              }
              style={inputStyle}
            />

            <select
              value={unit1}
              onChange={(e) =>
                setUnit1(e.target.value)
              }
              style={inputStyle}
            >
              {measurementUnits[type].map((u) => (
                <option key={u}>{u}</option>
              ))}
            </select>

            <h3>Second Quantity</h3>

            <input
              type="number"
              placeholder="Value"
              value={value2}
              onChange={(e) =>
                setValue2(e.target.value)
              }
              style={inputStyle}
            />

            <select
              value={unit2}
              onChange={(e) =>
                setUnit2(e.target.value)
              }
              style={inputStyle}
            >
              {measurementUnits[type].map((u) => (
                <option key={u}>{u}</option>
              ))}
            </select>

            <h3>Target Unit</h3>

            <select
              value={targetUnit}
              onChange={(e) =>
                setTargetUnit(
                  e.target.value
                )
              }
              style={inputStyle}
            >
              {measurementUnits[type].map((u) => (
                <option key={u}>{u}</option>
              ))}
            </select>
          </div>

          {/* RIGHT PANEL */}
          <div
            style={{
              background: "rgba(255,255,255,0.1)",
              padding: "25px",
              borderRadius: "20px",
              backdropFilter: "blur(10px)",
            }}
          >
            <h2>Operations</h2>

            <div
              style={{
                display: "flex",
                flexWrap: "wrap",
                gap: "15px",
                marginBottom: "30px",
              }}
            >
              {!disableTemperatureArithmetic && (
                <>
                  <ActionButton
                    title="ADD"
                    onClick={() =>
                      performOperation("add")
                    }
                  />

                  <ActionButton
                    title="SUBTRACT"
                    onClick={() =>
                      performOperation(
                        "subtract"
                      )
                    }
                  />

                  <ActionButton
                    title="DIVIDE"
                    onClick={() =>
                      performOperation(
                        "divide"
                      )
                    }
                  />
                </>
              )}

              <ActionButton
                title="COMPARE"
                onClick={() =>
                  performOperation("compare")
                }
              />

              <ActionButton
                title="CONVERT"
                onClick={() =>
                  performOperation("convert")
                }
              />
            </div>

            {disableTemperatureArithmetic && (
              <div
                style={{
                  background: "#ef4444",
                  padding: "12px",
                  borderRadius: "10px",
                  marginBottom: "20px",
                }}
              >
                Temperature supports only
                Compare & Convert
              </div>
            )}

            <h2>Result</h2>

            <pre
              style={{
                background: "#000",
                padding: "20px",
                borderRadius: "15px",
                minHeight: "250px",
                overflow: "auto",
                color: "#00ff88",
              }}
            >
              {result || "No result yet"}
            </pre>
          </div>
        </div>
      </div>
    </div>
  )
}

function ActionButton({
  title,
  onClick,
}) {
  return (
    <button
      onClick={onClick}
      style={{
        padding: "14px 25px",
        border: "none",
        borderRadius: "12px",
        background: "#2563eb",
        color: "white",
        fontSize: "16px",
        cursor: "pointer",
        fontWeight: "bold",
      }}
    >
      {title}
    </button>
  )
}

const inputStyle = {
  width: "100%",
  padding: "14px",
  marginTop: "10px",
  marginBottom: "20px",
  borderRadius: "10px",
  border: "none",
  fontSize: "16px",
  outline: "none",
}

export default Home