using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Basket : MonoBehaviour
{
    [Header("Definiowane dynamicznie")]
    public Text scoreGT;

    // Start is called before the first frame update
    void Start()
    {
        // Znajd� referencj� do obiektu gry ScoreCounter
        GameObject scoreGO = GameObject.Find("ScoreCounter");
        // Pobierz komponent text dla tego obiektu gry
        scoreGT = scoreGO.GetComponent<Text>();
        //Pocz�tkowa warto�� wyniku powinna by� r�wna 0
        scoreGT.text = "0";
    }

    // Update is called once per frame
    void Update()
    {
        // Pobie� obecne po�o�enie kursora myszy
        Vector3 mousePos2D = Input.mousePosition;

        // Po�o�enie kamery na osi Z oznacza jak daleko mo�na przemie�ci� mysz w przestrzeni 3D
        mousePos2D.z = -Camera.main.transform.position.z;

        // Konwersja punktu z dwuwymiarowej przestrzeni ekranu na tr�jwymiarow�
        Vector3 mousePos3D = Camera.main.ScreenToWorldPoint(mousePos2D);

        // Przypisaanie do parametru po�o�enia x obiektu Basket warto�ci po�o�enia x myszy
        Vector3 pos = this.transform.position;
        pos.x = mousePos3D.x;
        this.transform.position = pos;
        
    }

    void OnCollisionEnter(Collision coll)
    {
        // Ustalam co uderzy�o w kosz
        GameObject collidedWith = coll.gameObject;

        if(collidedWith.tag == "Apple")
        {
            Destroy(collidedWith);

            // zmie� tekst przechowywany w scoreGT na liczb� ca�kowit�
            int score = int.Parse(scoreGT.text);
            // dodaj punkty za z�apane jab�ka
            score += 100;
            // z powrotem zmieniam wynik na text i wy�wietlam go
            scoreGT.text = score.ToString();

            // zarz�dzaj najlepszym wynikiem
            if (score > HighScore.score)
            {
                HighScore.score = score;
            }
        }
    }
}
