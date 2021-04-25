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
        // ZnajdŸ referencjê do obiektu gry ScoreCounter
        GameObject scoreGO = GameObject.Find("ScoreCounter");
        // Pobierz komponent text dla tego obiektu gry
        scoreGT = scoreGO.GetComponent<Text>();
        //Pocz¹tkowa wartoœæ wyniku powinna byæ równa 0
        scoreGT.text = "0";
    }

    // Update is called once per frame
    void Update()
    {
        // Pobie¿ obecne po³o¿enie kursora myszy
        Vector3 mousePos2D = Input.mousePosition;

        // Po³o¿enie kamery na osi Z oznacza jak daleko mo¿na przemieœciæ mysz w przestrzeni 3D
        mousePos2D.z = -Camera.main.transform.position.z;

        // Konwersja punktu z dwuwymiarowej przestrzeni ekranu na trójwymiarow¹
        Vector3 mousePos3D = Camera.main.ScreenToWorldPoint(mousePos2D);

        // Przypisaanie do parametru po³o¿enia x obiektu Basket wartoœci po³o¿enia x myszy
        Vector3 pos = this.transform.position;
        pos.x = mousePos3D.x;
        this.transform.position = pos;
        
    }

    void OnCollisionEnter(Collision coll)
    {
        // Ustalam co uderzy³o w kosz
        GameObject collidedWith = coll.gameObject;

        if(collidedWith.tag == "Apple")
        {
            Destroy(collidedWith);

            // zmieñ tekst przechowywany w scoreGT na liczbê ca³kowit¹
            int score = int.Parse(scoreGT.text);
            // dodaj punkty za z³apane jab³ka
            score += 100;
            // z powrotem zmieniam wynik na text i wyœwietlam go
            scoreGT.text = score.ToString();

            // zarz¹dzaj najlepszym wynikiem
            if (score > HighScore.score)
            {
                HighScore.score = score;
            }
        }
    }
}
