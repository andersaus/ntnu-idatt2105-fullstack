describe('ContactComponent', () => {
  it('submits contact form with valid data', () => {
    cy.visit('/contact')
    
    cy.get('#name').type('John Doe')
    cy.get('#email').type('johndoe@example.com')
    cy.get('#feedback').type('This is a test feedback message.')
    cy.get('#submitbutton').click()

    cy.get('.success-message').should('contain', 'Thank you for your feedback!')
    cy.get('#name').should('have.value', '')
    cy.get('#email').should('have.value', '')
    cy.get('#feedback').should('have.value', '')
  })
})